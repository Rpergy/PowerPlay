package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime

class Intake (hardwareMap: HardwareMap) {
    private lateinit var leftExtension: Servo
    private lateinit var rightExtension: Servo
    private var extensionTimer = ElapsedTime()
    var extensionState: ExtensionState = ExtensionState.IDLE

    private lateinit var leftArm: Servo
    private lateinit var rightArm: Servo

    private lateinit var claw: Servo
    var clawState: ClawState = ClawState.OPEN

    init {
        if (hardwareMap.servo.contains("leftExtension")) {
            leftExtension = hardwareMap.servo.get("leftExtension")
            leftExtension.direction = Servo.Direction.REVERSE
            leftExtension.position = ActuationConstants.ExtensionConstants.RETRACTED
        }

        if (hardwareMap.servo.contains("rightExtension")) {
            rightExtension = hardwareMap.servo.get("rightExtension")
            rightExtension.position = ActuationConstants.ExtensionConstants.RETRACTED
        }

        if (hardwareMap.servo.contains("leftArm")) {
            leftArm = hardwareMap.servo.get("leftArm")
            leftArm.direction = Servo.Direction.REVERSE
            leftArm.position = ActuationConstants.ArmConstants.IDLE
        }

        if (hardwareMap.servo.contains("rightArm")) {
            rightArm = hardwareMap.servo.get("rightArm")
            rightArm.position = ActuationConstants.ArmConstants.IDLE
        }

        if (hardwareMap.servo.contains("claw")) {
            claw = hardwareMap.servo.get("claw")
            claw.position = ActuationConstants.ClawConstants.OPEN
        }
    }

    private fun retract() {
        if (extensionTimer.milliseconds() <= 200) {
            closeClaw()
        } else {
            leftArm.position = ActuationConstants.ArmConstants.IDLE
            rightArm.position = ActuationConstants.ArmConstants.IDLE
            leftExtension.position = ActuationConstants.ExtensionConstants.RETRACTED
            rightExtension.position = ActuationConstants.ExtensionConstants.RETRACTED
        }
    }

    private fun extend(armPosition: Double = ActuationConstants.ArmConstants.INTAKING) {
        leftArm.position = armPosition
        rightArm.position = armPosition
        leftExtension.position = ActuationConstants.ExtensionConstants.EXTENDED
        rightExtension.position = ActuationConstants.ExtensionConstants.EXTENDED
    }

    private fun deposit() {
        leftArm.position = ActuationConstants.ArmConstants.DEPOSITING
        rightArm.position = ActuationConstants.ArmConstants.DEPOSITING
    }

    fun openClaw() {
        clawState = ClawState.OPEN
    }

    fun closeClaw() {
        clawState = ClawState.CLOSED
    }

    fun updateExtensionState(state: ExtensionState) {
        if (state == ExtensionState.EXTENDING)
            extensionTimer.reset()
        extensionState = state
    }

    fun update(binds: List<Boolean>) {
        when(extensionState) {
            ExtensionState.IDLE -> retract()
            ExtensionState.EXTENDING -> extend()
            ExtensionState.DEPOSITING -> deposit()
        }

        when(clawState) {
            ClawState.OPEN -> claw.position = ActuationConstants.ClawConstants.OPEN
            ClawState.CLOSED -> claw.position = ActuationConstants.ClawConstants.CLOSED
        }

        if (binds[0]) {
            updateExtensionState(ExtensionState.EXTENDING)
        } else if (binds[1]) {
            updateExtensionState(ExtensionState.DEPOSITING)
        } else {
            updateExtensionState(ExtensionState.IDLE)
        }

        if (binds[2] && clawState == ClawState.OPEN) {
            closeClaw()
        } else if (binds[2] && clawState == ClawState.CLOSED) {
            openClaw()
        }
    }

    fun update() {
        when(extensionState) {
            ExtensionState.IDLE -> retract()
            ExtensionState.EXTENDING -> extend()
            ExtensionState.DEPOSITING -> deposit()
        }

        when(clawState) {
            ClawState.OPEN -> claw.position = ActuationConstants.ClawConstants.OPEN
            ClawState.CLOSED -> claw.position = ActuationConstants.ClawConstants.CLOSED
        }
    }

    enum class ExtensionState {
        IDLE,
        EXTENDING,
        DEPOSITING
    }

    enum class ClawState {
        OPEN,
        CLOSED
    }
}