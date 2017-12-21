package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.libraries.APDS9960;
import org.firstinspires.ftc.teamcode.libraries.AutoLib;
import org.firstinspires.ftc.teamcode.libraries.SensorLib;
import org.firstinspires.ftc.teamcode.opmodes.hardware.BotHardware;

/**
 * Created by Noah on 12/20/2017.
 */

@Autonomous(name="APDS Auto", group="test")
public class ADPSAuto extends OpMode {
    APDS9960 dist;
    APDS9960.Config config = new APDS9960.Config();
    BotHardware bot = new BotHardware(this);

    AutoLib.Sequence mSeq = new AutoLib.LinearSequence();

    public void init() {
        config.setPulse(APDS9960.Config.PulseLength.PULSE_16US, (byte)8, APDS9960.Config.LEDStrength.STREN_100MA, APDS9960.Config.LEDBoost.BOOST_1X, APDS9960.Config.DistGain.GAIN_8X);
        dist = new APDS9960(config, hardwareMap.get(I2cDeviceSynch.class, "dist"));
        dist.initDevice();

        bot.init();

        mSeq.add(new AutoLib.TimedServoStep(BotHardware.ServoE.stickBase.servo, 0.469, 0.5, false));
        mSeq.add(new AutoLib.TimedServoStep(BotHardware.ServoE.stick.servo, BotHardware.ServoE.stickDown - 0.1, 0.5, false));
        mSeq.add(new APDSFind(bot.getMotorVelocityShimArray(), dist, config, new SensorLib.PID(0.5f, 0.15f, 0, 10), 35.0f, 105.0f, 200, 3, this));
        //mSeq.add(new AutoLib.MoveByEncoderStep(bot.getMotorVelocityShimArray(), 35.0f, 500, true));

        dist.startDevice();
    }

    public void start() {
        dist.startDevice();
    }

    public void loop() {
        if(mSeq.loop()); //requestOpModeStop();
    }


    public void stop() {

    }

    private static class APDSFind extends AutoLib.Step {
        private final int mError;
        private final int mDist;
        private final DcMotor[] motorRay;
        private final APDS9960 sens;
        private final APDS9960.Config config;
        private final OpMode mode;
        private final SensorLib.PID errorPid;
        private final float minPower;
        private final float maxPower;

        private double lastTime = 0;
        private int foundCount = 0;

        private static final int APDS_FOUND_COUNT = 5;
        private static final int GAIN_LOWER_WINDOW = 40;

        APDSFind(DcMotor[] motors, APDS9960 sens, APDS9960.Config config, SensorLib.PID errorPid, float minPower, float maxPower, int dist, int error, OpMode mode) {
            this.motorRay = motors;
            this.config = config;
            this.errorPid = errorPid;
            this.sens = sens;
            this.minPower = minPower;
            this.maxPower = maxPower;
            this.mError = error;
            this.mDist = dist;
            this.mode = mode;
        }

        public boolean loop() {
            if(lastTime == 0) lastTime = mode.getRuntime();
            //get the distance and error
            int error = this.mDist - this.sens.getDist();
            //if we found it, stop
            //if the peak is within stopping margin, stop
            if(config.gain == APDS9960.Config.DistGain.GAIN_1X && Math.abs(error) <= mError) {
                setMotors(0);
                return ++foundCount >= APDS_FOUND_COUNT;
            }
            //else lower gain level until bottom out
            else if(config.gain != APDS9960.Config.DistGain.GAIN_1X && Math.abs(error) <= GAIN_LOWER_WINDOW) {
                APDS9960.Config.DistGain temp;
                if(config.gain == APDS9960.Config.DistGain.GAIN_8X) temp = APDS9960.Config.DistGain.GAIN_4X;
                else if(config.gain == APDS9960.Config.DistGain.GAIN_4X) temp = APDS9960.Config.DistGain.GAIN_2X;
                else if(config.gain == APDS9960.Config.DistGain.GAIN_2X) temp = APDS9960.Config.DistGain.GAIN_1X;
                else throw new IllegalArgumentException("boi");
                config.setPulse(config.len, config.count, config.strength, config.boost, temp);
                sens.initDevice();
                sens.startDevice();
            }
            //PID
            double time = mode.getRuntime();
            float pError = errorPid.loop(error, (float)(time - lastTime));
            lastTime = time;
            mode.telemetry.addData("power error", pError);
            //cut out a middle range, but handle positive and negative
            float power;
            if(pError >= 0) power = Range.clip(minPower + pError, minPower, maxPower);
            else power = Range.clip(pError - minPower, -maxPower, -minPower);
            setMotors(-power);
            //telem
            mode.telemetry.addData("Peak dist", error);
            //return
            return false;
        }

        private void setMotors(float power) {
            for(DcMotor motor : motorRay) motor.setPower(power);
        }
    }
}
