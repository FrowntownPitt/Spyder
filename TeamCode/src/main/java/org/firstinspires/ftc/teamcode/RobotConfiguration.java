package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class RobotConfiguration {
    public static final float MM_PER_INCH = 25.4f;
    public static final float MM_FTC_FIELD_WIDTH = (12*6) * MM_PER_INCH;

    static final float ROBOT_LENGTH = 25.5f;
    static final float ROBOT_WIDTH  = 25.5f;

    public final BNO055IMU IMU;
    public final String IMU_NAME = "imu";

    private DcMotor motorFront;
    private DcMotor motorRear;
    private DcMotor motorLeft;
    private DcMotor motorRight;

    private float offsetZ = 0;
    private float offsetY = 0;
    private float offsetX = 0;

    private Orientation imu_angles;

    public RobotConfiguration(HardwareMap hardwareMap) {
        IMU = hardwareMap.get(BNO055IMU.class, "imu");

        motorFront = hardwareMap.dcMotor.get("frontMotor");
        motorRear = hardwareMap.dcMotor.get("rearMotor");
        motorLeft = hardwareMap.dcMotor.get("leftMotor");
        motorRight = hardwareMap.dcMotor.get("rightMotor");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFront.setDirection(DcMotor.Direction.REVERSE);
    }

    public void updateIMUAngles(){
        imu_angles = getOrientation();
    }

    public void initializeIMU(){
        BNO055IMU.Parameters imuparams = new BNO055IMU.Parameters();
        imuparams.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuparams.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuparams.calibrationDataFile = "BNO055IMUCalibration.json";
        imuparams.loggingEnabled = true;
        imuparams.loggingTag = "IMU";

        IMU.initialize(imuparams);
    }

    public Orientation getOrientation(){
        return IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    public void resetIMUOffset(){
        offsetZ = imu_angles.firstAngle;
        offsetY = imu_angles.secondAngle;
        offsetX = imu_angles.thirdAngle;
    }

    public void setPowerFromAngle(double angle, double power, double rotation){
        double leftPower  = power * Math.sin(angle) + rotation;
        double rightPower = power * Math.sin(angle) - rotation;
        double frontPower = power * Math.cos(angle) + rotation;
        double rearPower  = power * Math.cos(angle) - rotation;

        motorLeft.setPower(leftPower);
        motorRight.setPower(rightPower);
        motorFront.setPower(frontPower);
        motorRear.setPower(rearPower);
    }

    public void setPowerFromJoysticks(double x, double y, double r){
        double power = Math.hypot(x, y);

        double targetAngle = Math.atan2(y, x) - (Math.toRadians(imu_angles.firstAngle - offsetZ));

        setPowerFromAngle(targetAngle, power, r);
    }

    public void displayIMUData(Telemetry telemetry) {
        updateIMUAngles();

        telemetry.addData("Heading ", imu_angles.firstAngle - offsetZ);
        telemetry.addData("Roll    ", imu_angles.secondAngle - offsetY);
        telemetry.addData("Pitch   ", imu_angles.thirdAngle - offsetX);
    }
}