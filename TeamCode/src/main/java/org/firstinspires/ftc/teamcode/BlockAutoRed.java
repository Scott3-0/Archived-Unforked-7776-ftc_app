package org.firstinspires.ftc.teamcode;

/**
 * Created by Robotics on 3/7/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.*;

@Autonomous(name="Red Block Auto (Shoot Balls, Defense)", group="Main")
@Disabled
public class BlockAutoRed extends OpMode {
    BotHardware robot = new BotHardware();

    BlockAuto auto = new BlockAuto(this, true);

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

