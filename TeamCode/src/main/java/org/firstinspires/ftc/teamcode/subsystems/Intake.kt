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
            claw.position = ActuationConstants.ClawConstants.OPEN
        }
    }

    fun autoInit() {
        leftArm.position = 0.53
        rightArm.position = 0.53
        claw.position = 0.69
    }

    private fun retract(extensionPosition: Double = ActuationConstants.ExtensionConstants.RETRACTED, armPosition: Double = ActuationConstants.ArmConstants.TRANSFER) {
        if (retractionTimer.milliseconds() <= 200) {
            updateClawState(ClawState.CLOSED)
        } else {
            leftArm.position = armPosition
            rightArm.position = armPosition
            leftExtension.position = extensionPosition
            rightExtension.position = extensionPosition
        }
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

    private fun deposit() {
        leftArm.position = ActuationConstants.ArmConstants.FIRST_JUNCTION
        rightArm.position = ActuationConstants.ArmConstants.FIRST_JUNCTION
    }

    fun updateExtensionState(state: ExtensionState, adjusted: Boolean = false, armPosition: Double = ActuationConstants.ArmConstants.DOWN) {
        if (state == ExtensionState.EXTENDING)
            retractionTimer.reset()
        else if (state == ExtensionState.IDLE) {
            extensionTimer.reset()
        }

        extensionState = state

        when(extensionState) {
            ExtensionState.IDLE -> if (adjusted) retract(ActuationConstants.ExtensionConstants.TRANSFER, ActuationConstants.ArmConstants.TRANSFER) else retract()
            ExtensionState.EXTENDING -> extend(armPosition)
            ExtensionState.DEPOSITING -> {
                if (adjusted) {
                    leftArm.position = ActuationConstants.ArmConstants.DOWN
                    rightArm.position = ActuationConstants.ArmConstants.DOWN
                } else {
                    deposit()
                }
            }
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
        } else if (binds[1] || binds[2]) {
            updateExtensionState(ExtensionState.DEPOSITING, binds[2])
        } else {
            updateExtensionState(ExtensionState.IDLE)
        }

        if (binds[3] && clawState == ClawState.OPEN) {
            updateClawState(ClawState.CLOSED)
        } else if (binds[3] && clawState == ClawState.CLOSED) {
            updateClawState(ClawState.OPEN)
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