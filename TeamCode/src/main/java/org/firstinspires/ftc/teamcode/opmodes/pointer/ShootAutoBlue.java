package org.firstinspires.ftc.teamcode.opmodes.pointer;

/**
 * Created by Robotics on 3/7/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.opmodes.ShootAuto;
import org.firstinspires.ftc.teamcode.opmodes.hardware.BotHardware;

@Autonomous(name="Blue Shoot Auto (Shoot Balls)", group="Main")
//@Disabled
public class ShootAutoBlue extends OpMode {
    BotHardware robot = new BotHardware();

    ShootAuto auto = new ShootAuto(this, false);

    @Override
    public void init(){
        auto.init();
    }

    @Override
    public void init_loop(){
        auto.init_loop();
    }

    @Override
    public void start(){
        auto.start();
    }

    @Override
    public void loop(){
        auto.loop();
    }

    @Override
    public void stop(){
        auto.stop();
    }
}