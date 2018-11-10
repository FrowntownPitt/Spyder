package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by frown on 6/30/2017.
 */

@TeleOp(name="Spyder", group="Spyder")
public class Holonomic extends OpMode {

    private RobotConfiguration robot;

    public void init(){
        robot = new RobotConfiguration(hardwareMap);
        robot.initializeIMU();
    }

    public void start(){
        robot.updateIMUAngles();
    }

    public void loop(){
        robot.updateIMUAngles();

        robot.displayIMUData(telemetry);

        if(gamepad1.dpad_left){
            robot.resetIMUOffset();
        }

        robot.setPowerFromJoysticks(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        telemetry.update();
    }
}
