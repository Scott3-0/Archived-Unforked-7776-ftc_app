package org.firstinspires.ftc.teamcode.opmodes.demo;

import com.qualcomm.hardware.adafruit.AdafruitI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import org.firstinspires.ftc.teamcode.libraries.hardware.APDS9960;

/**
 * Created by Noah on 2/21/2018.
 */

@Autonomous(name = "APDS + Color", group = "test")
public class ColorDistance extends OpMode {
    //APDS9960 dist;
    //APDS9960.Config distConfig = new APDS9960.Config();
    ColorSensor frontColor;
    ColorSensor backColor;

    public void init() {
        //final I2cDeviceSynch device = hardwareMap.get(I2cDeviceSynch.class, "color");
        //dist = new APDS9960(distConfig, device, true, APDS9960.Config.DistGain.GAIN_8X);
        //dist.initDevice();

        frontColor = hardwareMap.get(ColorSensor.class, "fc");
        backColor = hardwareMap.get(ColorSensor.class, "bc");
    }

    public void start() {
        //dist.startDevice();
    }

    public void loop() {
        //telemetry.addData("Distance", dist.getLinearizedDistance(false));
        telemetry.addData("Front Red", frontColor.red());
        telemetry.addData("Front Green", frontColor.green());
        telemetry.addData("Front Blue", frontColor.blue());
        telemetry.addData("Back Red", backColor.red());
        telemetry.addData("Back Green", backColor.green());
        telemetry.addData("Back Blue", backColor.blue());
    }
}
