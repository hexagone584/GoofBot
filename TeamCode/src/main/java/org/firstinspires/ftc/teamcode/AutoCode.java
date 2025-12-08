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

    //SPECS!!!!
    static final double ticksPerRev = 384.5; //see goBilDA 5203 435RPM spec sheets
    static final double wheelCircumference = Math.PI * 3.77953; //we're using inches, NOT mm
    static final double ticksPerInch = ticksPerRev / wheelCircumference; //meaning that ~32.38 ticks accounts for one inch traveled

    //helper function convert inches to 435RPM encoder ticks
    public int inchesToTicks(double inches) {
        return (int) (inches * ticksPerInch); //they'll truncate some floating points, but I think that's negligible for what we're doing
    }

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
    public void Drive(String direction, int distance, double power) {
        distance = inchesToTicks(distance);

        FL.setTargetPosition(FL.getCurrentPosition() + distance);
        FR.setTargetPosition(FR.getCurrentPosition() + distance);
        BL.setTargetPosition(BL.getCurrentPosition() + distance);
        BR.setTargetPosition(BR.getCurrentPosition() + distance);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        switch (direction) {
            case "forward":
                setDirectionPower("forward");
            case "back":
            case "left":
            case "right":
            case "rotateRight":
            case "rotateLeft":

        }

        while(opModeIsActive() && (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())){ }
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

        //Gate1 = hardwareMap.servo.get("Gate1");

        FL.setPower(-.5);
        FR.setPower(-.5);
        BL.setPower(-.5);
        BR.setPower(-.5);
        if (opModeIsActive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }

        }
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
        if (opModeIsActive()) {
            Drive("forward",2,.5);
            Drive("right",1,1);
            Drive("forward",9,1);
        }
    }
}

