package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by frown on 6/30/2017.
 */

@TeleOp(name="Spyder Logger", group="Spyder")
public class HolonomicLogging extends OpMode {

    private RobotConfiguration robot;

    private File dataFile;
    private FileOutputStream dataFileStream;

    public void init(){
        robot = new RobotConfiguration(hardwareMap);
        robot.initializeIMU();

        try {
            createFile("Holonomic_Encoders");
        } catch (FileNotFoundException e) {
            telemetry.addData("File exception", "cannot open file: " + e.getMessage());
        } catch (IOException e) {
            telemetry.addData("File exception", "cannot open file: " + e.getMessage());
        }
        telemetry.addData("File location", dataFile.getAbsolutePath());
        telemetry.update();
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

        double heading = robot.getHeading();
        int[] encoderValues = robot.getDriveEncoderValues();

        try {
            String dataLine = formatEncoderOutput(heading, encoderValues);
            writeToFile(dataLine);
            telemetry.addData("File write", "{" + dataLine + "}");
        } catch (IOException e) {
            telemetry.addData("File exception", "cannot write to file" + e.getMessage());
        }

        telemetry.update();
    }

    private String formatEncoderOutput(double heading, int[] encoderValues){
        return (String.format(Locale.getDefault(),
                "%f,%f,%d,%d,%d,%d\n", getRuntime(), heading, encoderValues[0], encoderValues[1], encoderValues[2], encoderValues[3]));
    }

    public void stop() {
        try {
            closeFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close file");
        }
    }

    private void createFile(String filename) throws IOException {
        AppUtil.getInstance().ensureDirectoryExists(AppUtil.ROBOT_DATA_DIR);

        int fileNumber = 0;
        dataFile = new File(AppUtil.ROBOT_DATA_DIR,
                String.format(Locale.getDefault(), "%s-%d.csv", filename, fileNumber));
        while (dataFile.exists()) {
            fileNumber += 1;
            dataFile = new File(AppUtil.ROBOT_DATA_DIR,
                    String.format(Locale.getDefault(), "%s-%d.csv", filename, fileNumber));
        }

        dataFile.createNewFile();

        dataFileStream = new FileOutputStream(dataFile);
    }

    private void writeToFile(String data) throws IOException {
        dataFileStream.write(data.getBytes());
    }

    private void closeFile() throws IOException {
        dataFileStream.close();
    }
}
