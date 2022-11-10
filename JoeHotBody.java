package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@TeleOp(name="JoeHotBody", group="Normal")

public class JoeHotBody extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private HotBodyHardware robot = new HotBodyHardware();


    private double heading = 0;
    private boolean fieldOriented = false;
    private double angleOffset;
    private double goalPos;
    private double lifterPos;

    private boolean autoLift;

    //switches
    private boolean xSwitch1 = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Tell the driver that initialization is complete.
        goalPos = 0;
        lifterPos = 0;
        autoLift = false;
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {
        runtime.reset();
        robot.init(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //average of two lifter motors
        lifterPos =  (robot.lifterMaster.getCurrentPosition() + robot.lifterSlave.getCurrentPosition()) / 2.0;
        //Heading = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle;

        if (gamepad1.x){
            if (!xSwitch1){//run once on press
                fieldOriented = !fieldOriented;
                angleOffset = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle;
            }
            xSwitch1 = true;
        }else{
            xSwitch1 = false;
        }

        if (fieldOriented){
            robot.fieldOrientedDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, angleOffset);
        }else {
            robot.simpleDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        }


        if(gamepad2.a){
            goalPos = 2100;
            autoLift = true;
        }
        if(gamepad2.b){
            goalPos = 3400;
            autoLift = true;
        }
        if(gamepad2.y){
            goalPos = 4350;
            autoLift = true;
        }
        if(gamepad2.x){
            goalPos = 0;
            autoLift = true;
        }

        if (!autoLift) {
            robot.lift((Math.abs(gamepad2.left_stick_y) > 0.1) ? gamepad2.left_stick_y : 0);
        }else{
            if(goalPos > lifterPos + 10) {
                robot.lift(-1);
            }else if (goalPos < lifterPos - 5) {
                robot.lift(1);
            }else{
                autoLift = false;
                robot.lift(0);
            }
        }


        robot.claw(gamepad2.left_trigger, gamepad2.right_trigger);


        telemetry.addData("JoeStatus", "Run Time: " + runtime.toString());
        telemetry.addData("JoeStatus", "Heading: " + heading);
        telemetry.addData("JoeStatus", "Field Oriented?: " + fieldOriented);
        telemetry.addData("JoeStatus", "lifterPos: " + lifterPos);
        telemetry.addData("JoeStatus", "lifterAutoMode: " + autoLift);
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
