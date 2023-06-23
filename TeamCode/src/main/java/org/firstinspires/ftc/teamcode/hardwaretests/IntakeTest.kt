package org.firstinspires.ftc.teamcode.hardwaretests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.subsystems.ActuationConstants

@TeleOp(name="Intake Test")
class IntakeTest: OpMode() {
    private lateinit var leftExtension: Servo
    private lateinit var rightExtension: Servo

    private lateinit var leftArm: Servo
    private lateinit var rightArm: Servo

    private lateinit var claw: Servo

    override fun init() {
        if (hardwareMap.servo.contains("leftExtension")) {
            leftExtension = hardwareMap.servo.get("leftExtension")
            leftExtension.position = ActuationConstants.ExtensionConstants.targetPosition
        }

        if (hardwareMap.servo.contains("rightExtension")) {
            rightExtension = hardwareMap.servo.get("rightExtension")
            rightExtension.position = ActuationConstants.ExtensionConstants.targetPosition
        }

        if (hardwareMap.servo.contains("leftArm")) {
            leftArm = hardwareMap.servo.get("leftArm")
            leftArm.position = ActuationConstants.ArmConstants.targetPosition
        }

        if (hardwareMap.servo.contains("rightArm")) {
            rightArm = hardwareMap.servo.get("rightArm")
            rightArm.position = ActuationConstants.ArmConstants.targetPosition
        }

        if (hardwareMap.servo.contains("claw")) {
            claw = hardwareMap.servo.get("claw")
            claw.position = ActuationConstants.ClawConstants.targetPosition
        }
    }

    override fun loop() {
        leftArm.position = ActuationConstants.ArmConstants.targetPosition
        rightArm.position = ActuationConstants.ArmConstants.targetPosition
        leftExtension.position = ActuationConstants.ExtensionConstants.targetPosition
        rightExtension.position = ActuationConstants.ExtensionConstants.targetPosition
        claw.position = ActuationConstants.ClawConstants.targetPosition

        telemetry.addData("Left Extension Position: ", leftExtension.position)
        telemetry.addData("Right Extension Position: ", rightExtension.position)
        telemetry.addData("Left Arm Position: ", leftArm.position)
        telemetry.addData("Right Arm Position: ", rightArm.position)
        telemetry.addData("Claw Position: ", claw.position)
        telemetry.update()
    }
}