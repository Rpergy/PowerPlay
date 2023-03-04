package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime

class Intake (hardwareMap: HardwareMap) {
    private lateinit var leftExtension: Servo
    private lateinit var rightExtension: Servo
    private var retractionTimer = ElapsedTime()
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
            claw.position = ActuationConstants.ClawConstants.INIT
        }
    }

    private fun retract() {
        if (retractionTimer.milliseconds() <= 200) {
            updateClawState(ClawState.CLOSED)
        } else {
            leftArm.position = ActuationConstants.ArmConstants.IDLE
            rightArm.position = ActuationConstants.ArmConstants.IDLE
            leftExtension.position = ActuationConstants.ExtensionConstants.RETRACTED
            rightExtension.position = ActuationConstants.ExtensionConstants.RETRACTED
        }
    }

    private fun transfer() {
        leftArm.position = ActuationConstants.ArmConstants.TRANSFER
        rightArm.position = ActuationConstants.ArmConstants.TRANSFER
        leftExtension.position = ActuationConstants.ExtensionConstants.TRANSFER
        rightExtension.position = ActuationConstants.ExtensionConstants.TRANSFER
    }

    private fun extend(armPosition: Double = ActuationConstants.ArmConstants.DOWN) {
        if (extensionTimer.milliseconds() <= 200) {
            updateClawState(ClawState.OPEN)
        } else {
            leftArm.position = armPosition
            rightArm.position = armPosition
            leftExtension.position = ActuationConstants.ExtensionConstants.EXTENDED
            rightExtension.position = ActuationConstants.ExtensionConstants.EXTENDED
        }
    }

    private fun deposit(armPosition: Double) {
        leftArm.position = armPosition
        rightArm.position = armPosition
    }

    fun updateExtensionState(state: ExtensionState, armPosition: Double = ActuationConstants.ArmConstants.DOWN) {
        if (state == ExtensionState.IDLE)
            extensionTimer.reset()
        if (state == ExtensionState.EXTENDING)
            retractionTimer.reset()

        extensionState = state

        when(extensionState) {
            ExtensionState.IDLE -> retract()
            ExtensionState.TRANSFERRING -> transfer()
            ExtensionState.EXTENDING -> extend(armPosition)
            ExtensionState.MANUAL_CONTROL -> deposit(armPosition)
        }
    }

    fun updateClawState(state: ClawState) {
        clawState = state

        when(clawState) {
            ClawState.OPEN -> claw.position = ActuationConstants.ClawConstants.OPEN
            ClawState.CLOSED -> claw.position = ActuationConstants.ClawConstants.CLOSED
        }
    }

    fun update(binds: List<Boolean>) {
        if (binds[0]) {
            updateExtensionState(ExtensionState.EXTENDING)
        } else if (binds[1]) {
            updateExtensionState(ExtensionState.MANUAL_CONTROL, ActuationConstants.ArmConstants.FIRST_JUNCTION)
        } else if (binds[2]) {
            updateExtensionState(ExtensionState.MANUAL_CONTROL, ActuationConstants.ArmConstants.DOWN)
        } else if (binds[3]) {
            updateExtensionState(ExtensionState.MANUAL_CONTROL, ActuationConstants.ArmConstants.coneStackPositions[0])
        } else if (binds[4]) {
            updateExtensionState(ExtensionState.MANUAL_CONTROL, ActuationConstants.ArmConstants.coneStackPositions[1])
        } else if (binds[5]) {
            updateExtensionState(ExtensionState.MANUAL_CONTROL, ActuationConstants.ArmConstants.coneStackPositions[2])
        } else if (binds[6]) {
            updateExtensionState(ExtensionState.MANUAL_CONTROL, ActuationConstants.ArmConstants.coneStackPositions[3])
        } else {
            updateExtensionState(ExtensionState.IDLE)
        }

        if (binds[7] && clawState == ClawState.OPEN) {
            updateClawState(ClawState.CLOSED)
        } else if (binds[7] && clawState == ClawState.CLOSED) {
            updateClawState(ClawState.OPEN)
        }
    }

    enum class ExtensionState {
        IDLE,
        TRANSFERRING,
        EXTENDING,
        MANUAL_CONTROL
    }

    enum class ClawState {
        OPEN,
        CLOSED
    }
}
