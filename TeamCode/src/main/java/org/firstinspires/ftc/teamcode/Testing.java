package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Testing")
public class Testing extends OpMode {
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotorEx Flywheel;
    Servo Gate1;
    Double flypower = powertorads(.46);
    boolean debounce = false;

    public double powertorads(double power) {
        return 6.525 * power - 0.28;
    }

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FrontLeftMotor");
        FR = hardwareMap.dcMotor.get("FrontRightMotor");
        BL = hardwareMap.dcMotor.get("BackLeftMotor");
        BR = hardwareMap.dcMotor.get("BackRightMotor");
        Flywheel = hardwareMap.get(DcMotorEx.class,"FlywheelMotor");
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
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

        FL.setPower((y-x-rx)/denominator);
        BL.setPower((y+x-rx)/denominator);
        FR.setPower((y+x+rx)/denominator);
        BR.setPower((y-x+rx)/denominator);


        Double guh = Gate1.getPosition();
        telemetry.addLine("Gate1 Position:");
        telemetry.addLine(guh.toString());
        telemetry.addLine("Flywheel power");
        telemetry.addLine(flypower.toString());

        if (gamepad1.dpadLeftWasPressed() && !debounce) {
            debounce = true;
            Gate1.setPosition(.75);
            try {
                Thread.sleep(400);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            Gate1.setPosition(.85);

            debounce = false;
        }

        if (gamepad1.dpadDownWasPressed()) {
            Gate1.setPosition(.86);
        }

        if (gamepad1.dpadUpWasPressed()) {
            Gate1.setPosition(.75);
        }


        if (gamepad1.xWasPressed()) {
            flypower += powertorads(.1);
        }

        if (gamepad1.aWasPressed()) {
            flypower -= powertorads(.1);
        }

        if (gamepad1.bWasPressed()) {
            flypower = 0.0;
        }

        if (gamepad1.yWasPressed()) {
            flypower = powertorads(.46);

        }

        if (gamepad1.left_bumper) {
            flypower = powertorads(1.5);
        }

        Flywheel.setVelocity(flypower,AngleUnit.RADIANS);
    }
}
