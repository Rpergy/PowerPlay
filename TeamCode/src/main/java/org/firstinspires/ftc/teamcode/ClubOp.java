package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;


@Disabled
@TeleOp(name="ClubOp")
public class ClubOp extends OpMode {
    SampleMecanumDrive drive;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        telemetry.addLine("Initialized!");

    }

    @Override
    public void loop() {
        drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad1.right_stick_y / 2,
                        -gamepad1.left_stick_x / 2,
                        -gamepad1.right_stick_x / 2)
        );


        drive.update(); // Update SampleMecanumDrive

        // Print the input from the left stick
        telemetry.addData("X: ", gamepad1.right_stick_x);
        telemetry.addData("Y: ", gamepad1.right_stick_y);
    }
}