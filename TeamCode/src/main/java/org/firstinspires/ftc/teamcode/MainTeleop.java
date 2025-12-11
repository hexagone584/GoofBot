package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "MainTeleop")
public class MainTeleop extends OpMode {
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor Flywheel;
    Servo Gate1;
    Double bleh = .84;
    Double power = 0.0;
    boolean debounce = false;

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FrontLeftMotor");
        FR = hardwareMap.dcMotor.get("FrontRightMotor");
        BL = hardwareMap.dcMotor.get("BackLeftMotor");
        BR = hardwareMap.dcMotor.get("BackRightMotor");
        Flywheel = hardwareMap.dcMotor.get("FlywheelMotor");
        //test
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        Flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Gate1 = hardwareMap.servo.get("Gate1");

        Gate1.setPosition(.85);
    }


    @Override
    public void loop() {
        double y = Math.pow(gamepad1.left_stick_y,7);
        double x = Math.pow(gamepad1.left_stick_x,7)*.9; // Counteract imperfect strafing
        double rx = Math.pow(gamepad1.right_stick_x,7);
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        FL.setPower((y-x-rx)/denominator / power);
        BL.setPower((y+x-rx)/denominator / power);
        FR.setPower((y+x+rx)/denominator / power);
        BR.setPower((y-x+rx)/denominator / power);

        Double guh = Gate1.getPosition();
        Double pluh = Math.cbrt(gamepad1.left_stick_y);
        telemetry.addLine("Gate1 Position:");
        telemetry.addLine(guh.toString());
        telemetry.addLine("Flywheel power");
        telemetry.addLine(power.toString());
        telemetry.addLine(pluh.toString());

        if (gamepad1.dpadLeftWasPressed() && !debounce) {
            debounce = true;
            Gate1.setPosition(.76);
            try {
                Thread.sleep(300);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            Gate1.setPosition(.85);

            debounce = false;
        }

        if (gamepad1.dpadDownWasPressed()) {
            Gate1.setPosition(.85);
        }

        if (gamepad1.dpadUpWasPressed()) {
            Gate1.setPosition(.76);
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

        if (gamepad1.left_bumper) {
            power = 1.0;
        }

        Flywheel.setPower(power);
    }
}
