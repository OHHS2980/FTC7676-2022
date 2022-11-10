package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class HotBodyHardware {
    public DcMotor backRight = null;
    public DcMotor backLeft = null;
    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;

    public DcMotor claw = null;
    public DcMotor lifterMaster = null;
    public DcMotor lifterSlave = null;
    public DcMotor lifterSlave2= null;

    public BNO055IMU imu = null;

    private double holdPosition1;
    private double holdPosition2;
    private double holdPosition3;

    private boolean clawOpen;
    HardwareMap hardwareMap = null;

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hardwareMap = ahwMap;


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");         //Get the left front drive motor from the hardware map
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");        //Get the right front drive motor from the hardware map
        backRight = hardwareMap.get(DcMotor.class, "backRight");         //Get the right back drive motor from the hardware map
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");          //Get the left back drive motor from the hardware map

        claw = hardwareMap.get(DcMotor.class, "claw");          //Get the claw motor from the hardware map
        lifterMaster = hardwareMap.get(DcMotor.class, "lifterMaster");          //Get the 1st lifter motor from the hardware map
        lifterSlave = hardwareMap.get(DcMotor.class, "lifterSlave");          //Get the 2nd lifter motor from the hardware map
        lifterSlave2 = hardwareMap.get(DcMotor.class, "lifterSlave2");

        //set left front drive motor
        frontLeft.setDirection(DcMotor.Direction.FORWARD);                        // The left front drive motor is set to forward
        frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);                     // The left front drive motor is set to run using encoder
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The left front drive motor is set to Brake when no power is applied

        //set right front drive motor
        frontRight.setDirection(DcMotor.Direction.REVERSE);                        // The right front drive motor is set to reverse
        frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);                     // The right front drive motor is set to run using encoder
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The right front drive motor is set to Brake when no power is applied

        //set left back drive motor
        backLeft.setDirection(DcMotor.Direction.FORWARD);                        // The left back drive motor is set to forward
        backLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                     // The left back drive motor is set to run using encoder
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The left back drive motor is set to Brake when no power is applied

        //set right back drive motor
        backRight.setDirection(DcMotor.Direction.REVERSE);                        // The right back drive motor is set to reverse
        backRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);                     // The right back drive motor is set to run using encoder
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The right back drive motor is set to Brake when no power is applied


        //set claw motor
        claw.setDirection(DcMotor.Direction.FORWARD);                        // The claw motor is set to forward
        claw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The claw motor is set to Brake when no power is applied

        //set 1st lifter motor
        lifterMaster.setDirection(DcMotor.Direction.FORWARD);                        // The 1st lifter motor is set to reverse
        lifterMaster.setMode(DcMotor.RunMode.RESET_ENCODERS);
        lifterMaster.setMode(DcMotor.RunMode.RUN_USING_ENCODER);                     // The 1st lifter motor is set to run using encoder
        lifterMaster.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The 1st lifter motor is set to Brake when no power is applied

        //set 2nd lifter motor
        lifterSlave.setDirection(DcMotor.Direction.FORWARD);                        // The 2nd lifter motor is set to reverse
        lifterSlave.setMode(DcMotor.RunMode.RESET_ENCODERS);
        lifterSlave.setMode(DcMotor.RunMode.RUN_USING_ENCODER);                     // The 2nd lifter motor is set to run using encoder
        lifterSlave.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The 2nd lifter motor is set to Brake when no power is applied

        //set 3rd lifter motor
        lifterSlave2.setDirection(DcMotor.Direction.FORWARD);                        // The 2nd lifter motor is set to reverse
        lifterSlave2.setMode(DcMotor.RunMode.RESET_ENCODERS);
        lifterSlave2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);                     // The 2nd lifter motor is set to run using encoder
        lifterSlave2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);          // The 2nd lifter motor is set to Brake when no power is applied


        holdPosition1 = lifterMaster.getCurrentPosition();
        holdPosition2 = lifterSlave.getCurrentPosition();
        holdPosition3 = lifterSlave2.getCurrentPosition();

        clawOpen = true;
    }

    public void simpleDrive(double joystick1X, double joystick1Y, double joystick2X){
        double y = joystick1Y;
        double x = -joystick1X * 1.1;
        double rx = -joystick2X;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        frontLeft.setPower((y + x + rx) / denominator) ;
        backLeft.setPower((y - x + rx) / denominator);
        frontRight.setPower((y - x - rx) / denominator);
        backRight.setPower((y + x - rx) / denominator);
    }

    public void fieldOrientedDrive(double joystick1X, double joystick1Y, double joystick2X, double angleOffset){
        double y = joystick1Y;
        double x = -joystick1X * 1.1;
        double rx = -joystick2X;

        double botHeading = -((imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle - angleOffset) * Math.PI / 180);

        double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
        double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        frontLeft.setPower((rotY + rotX + rx) / denominator) ;
        backLeft.setPower((rotY - rotX + rx) / denominator);
        frontRight.setPower((rotY - rotX - rx) / denominator);
        backRight.setPower((rotY + rotX - rx) / denominator);
    }

    public void HoldAngle(double goalAngle){
        double angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

        if (goalAngle > angle - 3 && goalAngle < angle + 3){
            simpleDrive(0,0,0);
        }else if (goalAngle > angle){
            simpleDrive(0,0,-Math.min(((goalAngle - angle) / 20), 1));
        }else{
            simpleDrive(0,0,Math.min(((angle - goalAngle) / 20), 1));
        }
    }

    public void lift(double power){
        double lifterPos = (-lifterMaster.getCurrentPosition() + lifterSlave.getCurrentPosition() + lifterSlave2.getCurrentPosition()) / 3.0;
        
        if (power == 0) {
            //hold the lifters position when the user lets go of the joystick
            /*
            lifterMaster.setPower((holdPosition1 - lifterPos) * 0.005 + 0.25);
            lifterSlave.setPower((holdPosition2 - lifterPos) * 0.005 + 0.25);
            lifterSlave2.setPower((holdPosition3 - lifterPos) * 0.005 + 0.25);
             */

            lifterMaster.setPower(0);
            lifterSlave.setPower(0);
            lifterSlave2.setPower(0);
        } else {
            holdPosition1 = lifterMaster.getCurrentPosition();
            holdPosition2 = lifterSlave.getCurrentPosition();
            holdPosition3 = lifterSlave2.getCurrentPosition();


            lifterMaster.setPower(power);
            lifterSlave.setPower(power);
            lifterSlave2.setPower(power);
        }
    }

    public void claw(double left, double right){
        if (!clawOpen){
            claw.setPower(-0.7);
        }else{
            claw.setPower(0);
        }

        if (left > 0.4){
            clawOpen = true;
            claw.setPower(left * 0.5);
        }else if (right > 0.4){
            clawOpen = false;
            claw.setPower(-right * 0.5);
        }
    }
}

