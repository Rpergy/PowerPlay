package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo

class Lift (hardwareMap: HardwareMap){
    private lateinit var lift: DcMotorEx
    private lateinit var depositor: Servo
    var depositorState: DepositorState = DepositorState.DOWN

    init {
        if (hardwareMap.dcMotor.contains("lift")) {
            lift = hardwareMap.dcMotor.get("lift") as DcMotorEx
            lift.power = 1.0
            setLiftPosition(0)
            lift.mode = DcMotor.RunMode.RUN_TO_POSITION
            lift.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }

        if (hardwareMap.servo.contains("depositor")) {
            depositor = hardwareMap.servo.get("depositor")
            depositor.direction = Servo.Direction.REVERSE
            depositor.position = ActuationConstants.DepositorConstants.DOWN
        }
    }

    fun setLiftPosition(target: Int) {
        lift.targetPosition = target
    }

    private fun idle() {
        depositor.position = ActuationConstants.DepositorConstants.DOWN
    }

    private fun deposit() {
        depositor.position = ActuationConstants.DepositorConstants.UP
    }

    fun updateDepositorState(state: DepositorState) {
        depositorState = state

        when (depositorState) {
            DepositorState.DOWN -> idle()
            DepositorState.UP -> deposit()
        }
    }

    fun update(binds: List<Boolean>) {
        if (binds[0]) {
           setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[0])
        }
        if (binds[1]) {
            setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[1])
        }
        if (binds[2]) {
            setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[2])
        }

        if (binds[3]) {
            updateDepositorState(DepositorState.UP)
        } else {
            updateDepositorState(DepositorState.DOWN)
        }
    }

    enum class DepositorState {
        UP,
        DOWN
    }
}