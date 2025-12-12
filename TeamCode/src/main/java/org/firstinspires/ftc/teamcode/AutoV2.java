package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoV2")
public class AutoV2 extends LinearOpMode {

    public double powertorads(double power) {
        return 6.525 * power - 0.2;
    }
    //helper sleep function idk
    public void mimi(long milliseconds) {
        try {
            Thread.sleep(milliseconds);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // instantiate our motors and servos
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor Flywheel;
    Servo Gate1;


    //function to abstract commands (aka im too lazy to copy and paste)
    public void Drive(String direction, long seconds, double power) {
        double y = 0.0;
        double x = 0.0;
        double rx = 0.0;

        switch (direction) {
            case "forward":
                y = 1.0;
                break;
            case "backward":
                y = -1.0;
                break;
            case "left":
                x = -1.0;
                break;
            case "right":
                x = 1.0;
                break;
            case "rotateRight":
                rx = 1.0;
                break;
            case "rotateLeft":
                rx = -1.0;
                break;
        }


        FL.setPower((y-x-rx) * power);
        BL.setPower((y+x-rx) * power);
        FR.setPower((y+x+rx) * power);
        BR.setPower((y-x+rx) * power);

        mimi((long) seconds * 1000);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        //this is to make sure it stops before doing other commands
        mimi((long) 1000);
    }


    //MAIN CODE
    //unlike opMode, there is no said loop nor initiation, and it's run by one single function
    @Override
    public void runOpMode() {
        FL = hardwareMap.dcMotor.get("FrontLeftMotor");
        FR = hardwareMap.dcMotor.get("FrontRightMotor");
        BL = hardwareMap.dcMotor.get("BackLeftMotor");
        BR = hardwareMap.dcMotor.get("BackRightMotor");
        Gate1 = hardwareMap.servo.get("Gate1");
        Flywheel = hardwareMap.dcMotor.get("FlywheelMotor");
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        Flywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        Gate1.setPosition(.85); //resets to correct position
        waitForStart(); //needs to continuously wait I suppose

        if (opModeIsActive()) {
            Flywheel.setPower(.50);
            mimi((long) 1500);

            for (int i = 0; i < 5; i++) {
                Gate1.setPosition(.75);
                mimi((long) 350);
                Gate1.setPosition(.85);
                mimi((long) 800);
            }

            Drive("forward", (long) 1, .30);
            Drive("left", (long) 1, .30);
            Gate1.setPosition(.85);
        }
    }
}

