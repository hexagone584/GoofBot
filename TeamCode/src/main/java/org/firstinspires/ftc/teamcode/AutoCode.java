package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoCode")
public class AutoCode extends LinearOpMode {
    // instantiate our motors and servos
    DcMotor FL;
    DcMotor FR;
    DcMotor BL;
    DcMotor BR;
    DcMotor Flywheel;
    Servo Gate1;
    ElapsedTime runtime;

    //helper function to convert direction into power values (taken from our teleop)
    public void setDirectionPower(String direction) {
        double y = 0;
        double x = 0;
        double rx = 0;
        switch (direction) {
            case "forward":
                y = 1;
            case "back":
                y = -1;
            case "left":
                x = -1;
            case "right":
                x = 1;
            case "rotateRight":
                rx = 1;
            case "rotateLeft":
                rx = -1;
        }


        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        FL.setPower((y-x-rx) / denominator);
        BL.setPower((y+x-rx) / denominator);
        FR.setPower((y+x+rx) / denominator);
        BR.setPower((y-x+rx) / denominator);
    }


    //function to abstract commands (aka im too lazy to copy and paste)
    public void Drive(String direction, long milliseconds) {
        setDirectionPower(direction);

        try {
            Thread.sleep(milliseconds);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }


    //MAIN CODE
    //unlike opMode, there is no said loop nor initiation, and it's run by one single function
    @Override
    public void runOpMode() {
        FL = hardwareMap.dcMotor.get("FrontLeftMotor");
        FR = hardwareMap.dcMotor.get("FrontRightMotor");
        BL = hardwareMap.dcMotor.get("BackLeftMotor");
        BR = hardwareMap.dcMotor.get("BackRightMotor");
        Flywheel = hardwareMap.dcMotor.get("FlywheelMotor");
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        Flywheel.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart(); //needs to continuously wait I suppose

        if (opModeIsActive()) {
            Drive("foward")
        }
    }
}

