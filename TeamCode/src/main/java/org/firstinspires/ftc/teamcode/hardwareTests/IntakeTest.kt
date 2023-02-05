package org.firstinspires.ftc.teamcode.hardwareTests

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS
import org.firstinspires.ftc.teamcode.hardware.ActuationConstants

@TeleOp(name="Intake Test")
class IntakeTest: OpMode() {
    lateinit var leftExtension: Servo
    lateinit var rightExtension: Servo

    lateinit var leftArm: Servo
    lateinit var rightArm: Servo

    lateinit var claw: Servo

    lateinit var gamepadEvent1: GamepadEventPS

    override fun init() {
        if (hardwareMap.servo.contains("leftExtension")) {
            leftExtension = hardwareMap.servo.get("leftExtension")
            leftExtension.direction = Servo.Direction.REVERSE
            leftExtension.position = 0.0
        }

        if (hardwareMap.servo.contains("rightExtension")) {
            rightExtension = hardwareMap.servo.get("rightExtension")
            rightExtension.position = 0.0
        }

        if (hardwareMap.servo.contains("leftArm")) {
            leftArm = hardwareMap.servo.get("leftArm")
            leftArm.direction = Servo.Direction.REVERSE
            leftArm.position = ActuationConstants.ArmConstants.IDLE
        }

        if (hardwareMap.servo.contains("rightArm")) {
            rightArm = hardwareMap.servo.get("rightArm")
            rightArm.position = 0.2
            rightArm.position = ActuationConstants.ArmConstants.IDLE
        }

        if (hardwareMap.servo.contains("claw")) {
            claw = hardwareMap.servo.get("claw")
            claw.position = 0.0
        }

        gamepadEvent1 = GamepadEventPS(gamepad1)
    }

    override fun loop() {
        if (gamepadEvent1.dPadUp()) {
            leftExtension.position += 0.05
            rightExtension.position += 0.05

        }

        if (gamepadEvent1.dPadDown()) {
            leftExtension.position -= 0.05
            rightExtension.position -= 0.05
        }


        telemetry.addData("Left Extension Position: ", leftExtension.position)
        telemetry.addData("Right Extension Position: ", rightExtension.position)
        telemetry.addData("Left Arm Position: ", leftArm.position)
        telemetry.addData("Right Arm Position: ", rightArm.position)
        telemetry.addData("Claw Position: ", claw.position)
        telemetry.update()
    }
}