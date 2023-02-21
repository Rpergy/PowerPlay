package org.firstinspires.ftc.teamcode.hardwaretests

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS
import org.firstinspires.ftc.teamcode.subsystems.ActuationConstants

@TeleOp(name = "Lift Test")
class LiftTest: OpMode() {
    lateinit var lift: DcMotorEx
    lateinit var depositor: Servo

    lateinit var gamepadEvent1: GamepadEventPS

    override fun init() {
        if (hardwareMap.dcMotor.contains("lift")) {
            lift = hardwareMap.dcMotor.get("lift") as DcMotorEx
            lift.power = 1.0
            lift.targetPosition = 0
            lift.mode = DcMotor.RunMode.RUN_TO_POSITION
            lift.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }

        if (hardwareMap.servo.contains("depositor")) {
            depositor = hardwareMap.servo.get("depositor")
            depositor.direction = Servo.Direction.REVERSE
            depositor.position = 0.0
        }
    }

    override fun loop() {
        lift.targetPosition = ActuationConstants.LiftConstants.targetPosition
        depositor.position = ActuationConstants.DepositorConstants.targetPosition

        telemetry.addData("Lift position", lift.targetPosition)
        telemetry.addData("Depositor position", depositor.position)
    }
}