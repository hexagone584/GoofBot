package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MainTeleop")
public class MainTeleop extends OpMode {
    //Initializes the variable to be used in init() and loop()
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor Flywheel;
    Servo Gate1;
    Double power = 0.0;
    boolean manualServo = false;
    Double gatePosition;

    //used to assign the motors based off of our Control Hub Schematics
    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FrontLeftMotor");
        FR = hardwareMap.dcMotor.get("FrontRightMotor");
        BL = hardwareMap.dcMotor.get("BackLeftMotor");
        BR = hardwareMap.dcMotor.get("BackRightMotor");
        //We need to reverse the right motors due to its orientation (pretty obvious)
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);


        //Flywheel/Servo
        Flywheel = hardwareMap.dcMotor.get("FlywheelMotor");
        //on our physical configuration, setting the reverse makes sense
        //being that positive values will release the ball
        Flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Gate1 = hardwareMap.servo.get("Gate1");

    }

    //
    //MAIN PROGRAM
    //
    @Override
    public void loop() {
        //takes in input of joysticks
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * .9; // Counteract imperfect strafing;
        double rx = gamepad1.right_stick_x;
        //Maintains the ratio if at least one is out of the range
        //ex: if you want to strafe left and turn left, the front right wheel will experience
        //a power of 2, which will be truncated to 1 and therefore makes the ratio imbalanced
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        FL.setPower((y-x-rx) / denominator);
        BL.setPower((y+x-rx) / denominator);
        FR.setPower((y+x+rx) / denominator);
        BR.setPower((y-x+rx) / denominator);

        //under the fantasized ideal world, we should have the servo and flywheel work in conjunction
        //however, this provides a nice backup plan by allowing manual control of the servo
        if (gamepad1.dpadLeftWasPressed()) {
            manualServo = false;
        }

         gatePosition = Gate1.getPosition();

        //driver gets these info
        telemetry.addLine("Gate1 Position:");
        telemetry.addLine(gatePosition.toString());
        telemetry.addLine("Flywheel power");
        telemetry.addLine(power.toString());

        if (gamepad1.dpadDownWasPressed()) {
            Gate1.setPosition(.85);
        }

        if (gamepad1.dpadUpWasPressed()) {
            Gate1.setPosition(.50);
        }

        if (gamepad1.xWasPressed()) {
            power += .01;
        }

        if (gamepad1.aWasPressed()) {
            power -= .01;
        }

        if (gamepad1.bWasPressed()) {
            power = .0;
        }

        if (gamepad1.yWasPressed()) {
            power = .50;
        }

        Flywheel.setPower(power);
    }
}
