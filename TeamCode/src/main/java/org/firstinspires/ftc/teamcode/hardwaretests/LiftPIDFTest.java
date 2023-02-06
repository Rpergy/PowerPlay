package org.firstinspires.ftc.teamcode.hardwaretests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.ActuationConstants;

@Config
@TeleOp(name = "Lift PIDF Test")
public class LiftPIDFTest extends OpMode {
    PIDController controller;

    public static double p = 0.005, i = 0, d = 0;
    public static double f = 0.1;

    public static int target = 0;

    final double ticksInDegrees = 537.6/180;
    DcMotorEx lift;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        lift = hardwareMap.get(DcMotorEx.class, "lift");
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int position = lift.getCurrentPosition();
        double pid = controller.calculate(position, target);
        double ff = Math.cos(Math.toRadians(target / ticksInDegrees)) * ActuationConstants.LiftConstants.f;
        lift.setPower(pid + ff);

        telemetry.addData("Lift Position", position);
        telemetry.addData("Target", target);
    }
}
