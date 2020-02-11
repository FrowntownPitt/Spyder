package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Locale;

@TeleOp(name = "Spyder Odometry", group = "Spyder")
public class HolonomicTracking extends OpMode {

    private RobotConfiguration robot;
    private DataLogger localizationLogger;

    public void init() {
        robot = new RobotConfiguration(hardwareMap);
        robot.initializeIMU();

        localizationLogger = new DataLogger("Holonomic_Tracking", telemetry);
    }

    public void start() {
        robot.updateIMUAngles();
        robot.initLocalizer();
    }

    public void loop() {
        robot.updateIMUAngles();
        robot.updateLocalizer();


        if (gamepad1.dpad_left) {
            robot.resetIMUOffset();
        }

        robot.setPowerFromJoysticks(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);


        String dataLine = formatLocalizationOutput(robot.getHeading(), robot.getDriveEncoderValues(), robot.getFieldLocation());
        localizationLogger.writeToFile(dataLine);

        robot.displayIMUData(telemetry);
        robot.displayFieldLocation(telemetry);

        telemetry.update();
    }

    private String formatLocalizationOutput(double heading, int[] encoderValues, RobotLocalizer.Location fieldLocation) {
        return (String.format(Locale.getDefault(),
                "%f,%f,%d,%d,%d,%d, %f,%f\n", getRuntime(), heading,
                encoderValues[0], encoderValues[1], encoderValues[2], encoderValues[3],
                fieldLocation.getX(), fieldLocation.getY()));
    }

    public void stop() {
        localizationLogger.closeFile();
    }
}
