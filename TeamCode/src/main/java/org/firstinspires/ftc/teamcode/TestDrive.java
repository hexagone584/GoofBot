package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "TestDrive")
public class TestDrive extends OpMode {
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;

    @Override
    public void init() {
        FL = hardwareMap.dcMotor.get("FrontLeftMotor");
        FR = hardwareMap.dcMotor.get("FrontRightMotor");
        BL = hardwareMap.dcMotor.get("BackLeftMotor");
        BR = hardwareMap.dcMotor.get("BackRightMotor");
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;
        //joypad thing
//        FL.setPower((gamepad1.dpad_up ? 1d : 0d) + (gamepad1.dpad_left ? 1d : 0d));
//        BL.setPower((gamepad1.dpad_up ? 1d : 0d) + (gamepad1.dpad_left ? 1d : 0d));
//        FR.setPower((gamepad1.dpad_up ? 1d : 0d) + (gamepad1.dpad_right ? 1d : 0d));
//        BR.setPower((gamepad1.dpad_up ? 1d : 0d) + (gamepad1.dpad_right ? 1d : 0d));
//        FL.setPower((gamepad1.dpad_down ? -1d : 0d) + (gamepad1.dpad_right ? -1d : 0d));
//        BL.setPower((gamepad1.dpad_down ? -1d : 0d) + (gamepad1.dpad_right ? -1d : 0d));
//        FR.setPower((gamepad1.dpad_down ? -1d : 0d) + (gamepad1.dpad_left ? -1d : 0d));
//        BR.setPower((gamepad1.dpad_down ? -1d : 0d) + (gamepad1.dpad_left ? -1d : 0d));
        //split control
//        FL.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
//        BL.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);
//        FR.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);
//        BR.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);
        //mecanum arcade drive
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;
        // Denominator is the largest motor power (absolute value) or 1
// This ensures all the powers maintain the same ratio, but only when
// at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        FL.setPower((y-x-rx) / denominator);
        BL.setPower((y+x-rx) / denominator);
        FR.setPower((y+x+rx) / denominator);
        BR.setPower((y-x+rx) / denominator);
    }
}
