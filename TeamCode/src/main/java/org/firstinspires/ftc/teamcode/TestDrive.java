package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
//        DcMotor FR = hardwareMap.dcMotor.get("FrontRightMotor");
//        DcMotor BL = hardwareMap.dcMotor.get("BackLeftMotor");
//        DcMotor BR = hardwareMap.dcMotor.get("BackLeftMotor");
    }

    @Override
    public void loop() {

        FL.setPower((-gamepad1.left_stick_y)-gamepad2.left_stick_x);
//        BL.setPower(-gamepad1.left_stick_y);
//        FR.setPower(-gamepad1.left_stick_y);
//        BR.setPower(-gamepad1.left_stick_y);
    }
}
