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
    BotHardware bot = new BotHardware(this);

    public void init() {
        bot.init();
    }

    public void start() {

    }

    public void loop() {
        bot.setLeftDrive(gamepad1.left_stick_y);
        bot.setRightDrive(gamepad1.right_stick_y);
    }

    public void stop() {
        bot.stopAll();
    }
}
