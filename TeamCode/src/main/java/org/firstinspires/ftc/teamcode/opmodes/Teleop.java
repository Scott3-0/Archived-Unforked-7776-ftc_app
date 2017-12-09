package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.libraries.FilterLib;
import org.firstinspires.ftc.teamcode.opmodes.hardware.BotHardware;

/**
 * Created by Noah on 10/27/2017.
 * Teleop!
 */

@TeleOp(name="Teleop")
public class Teleop extends OpMode {
    private static final float slowFactor = 0.5f;

    BotHardware bot = new BotHardware(this);
    private boolean suckerDown = false;
    private boolean lastA = false;
    private boolean robotSlow = false;
    private boolean lastBumper = false;

    private boolean lastBumper2 = false;
    private boolean dropperDown = false;

    public void init() {
        bot.init();
    }

    public void start() {

    }

    public void loop() {
        if(gamepad1.a && !lastA) robotSlow = !robotSlow;
        lastA = gamepad1.a;

        if((gamepad1.left_bumper || gamepad1.right_bumper) && !lastBumper) suckerDown = !suckerDown;
        lastBumper = gamepad1.left_bumper || gamepad1.right_bumper;

        if(gamepad1.left_trigger > 0) bot.setDropPos(BotHardware.ServoE.backDropUp + (gamepad1.left_trigger * (BotHardware.ServoE.backDropDown - BotHardware.ServoE.backDropUp)));
        else bot.setDropPos(BotHardware.ServoE.backDropUp);

        telemetry.addData("Drop", BotHardware.ServoE.backDropLeft.servo.getPosition());

        //if(suckerDown) bot.dropFront();
        //else bot.raiseFront();

        //if(gamepad2.left_trigger > 0) bot.setLift(gamepad2.left_trigger);
        //else if (gamepad2.right_trigger > 0) bot.setLift(-gamepad2.right_trigger);
        //else bot.setLift(0);

        if((gamepad2.left_bumper || gamepad2.right_bumper) && !lastBumper2) dropperDown = !dropperDown;
        lastBumper2 = gamepad2.left_bumper || gamepad2.right_bumper;

        //if(dropperDown) bot.dropBack();
        //else bot.raiseBack();

        if(robotSlow) {
            bot.setLeftDrive(gamepad1.left_stick_y * slowFactor);
            bot.setRightDrive(gamepad1.right_stick_y * slowFactor);
        }
        else {
            bot.setLeftDrive(gamepad1.left_stick_y);
            bot.setRightDrive(gamepad1.right_stick_y);
        }


        if(gamepad1.b) bot.dropStick();
        else bot.liftStick();

        bot.getStickBase().setPosition(BotHardware.ServoE.stickBaseHidden);

        telemetry.addData("Front Drop", suckerDown);
        telemetry.addData("Robot Slow", robotSlow);
    }

    public void stop() {
        bot.stopAll();
    }
}
