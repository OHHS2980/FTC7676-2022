package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name= "DeadReckoningBlue")

public class DesperationDeadReckoning extends LinearOpMode {
    private HotBodyHardware robot = new HotBodyHardware();
    private ElapsedTime runtime = new ElapsedTime();

    private double startTime;

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");  //Puts on the Driver Station that the robot is Initialized
        telemetry.update();                                       //Keeps on updating the phone if the robot is still Initialized

        waitForStart();                                           // Waits for the robot to start
        runtime.reset();                                          //Reset the runtime on the robot
        robot.init(hardwareMap);

        startTime = runtime.seconds();

        double startTime = runtime.seconds();

        while (opModeIsActive() && (runtime.seconds() - startTime) < 2) {
            robot.simpleDrive(0, -1, 0);
        }

        robot.simpleDrive(0, 0, 0);
        while (opModeIsActive()){
        }
    }
}