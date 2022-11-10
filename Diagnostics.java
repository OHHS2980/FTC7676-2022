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

@TeleOp(name="Diagnostics", group="Normal")

public class Diagnostics extends OpMode
{
    private HotBodyHardware robot = new HotBodyHardware();

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start(){
        robot.init(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {




        telemetry.addData("ROBOT STATUS:", "");
        telemetry.addData("   JoeStatus", "Heading: " + robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle);
        telemetry.addData("   JoeStatus", "Front Left Pos: " + robot.frontLeft.getCurrentPosition());
        telemetry.addData("   JoeStatus", "Front Right Pos: " + robot.frontRight.getCurrentPosition());
        telemetry.addData("   JoeStatus", "Back Left Pos: " + robot.backLeft.getCurrentPosition());
        telemetry.addData("   JoeStatus", "Back Right Pos: " + robot.backRight.getCurrentPosition());
        telemetry.addData("CONTROLLER STATUS:", "");
        telemetry.addData("   ControlStatus", "control1 Joy Left X: " + gamepad1.left_stick_x);
        telemetry.addData("   ControlStatus", "control1 Joy Left Y: " + gamepad1.left_stick_y);
        telemetry.addData("   ControlStatus", "control1 Joy Right X: " + gamepad1.right_stick_x);
        telemetry.addData("   ControlStatus", "control1 Joy Right Y: " + gamepad1.right_stick_y);
        telemetry.update();
    }
}
