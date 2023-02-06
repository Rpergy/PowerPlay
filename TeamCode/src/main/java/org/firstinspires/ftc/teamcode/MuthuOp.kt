package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.outoftheboxrobotics.photoncore.PhotonCore
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.ActuationConstants
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Lift

@TeleOp(name = "MuthuOp")
class MuthuOp: OpMode() {
    private lateinit var drive: SampleMecanumDrive
    private lateinit var intake: Intake
    private lateinit var lift: Lift
    private lateinit var gamepadEvent1: GamepadEventPS
    private lateinit var gamepadEvent2: GamepadEventPS
    private lateinit var timer: ElapsedTime
    private var mode = Mode.DRIVER_CONTROLLED

    private var slowMode = false

    override fun init() {
        drive = SampleMecanumDrive(hardwareMap)
        intake = Intake(hardwareMap)
        lift = Lift(hardwareMap)
        gamepadEvent1 = GamepadEventPS(gamepad1)
        gamepadEvent2 = GamepadEventPS(gamepad2)
        timer = ElapsedTime()

        telemetry.addLine("Initialized!")
        telemetry.update()
    }

    override fun loop() {
        PhotonCore.enable()

        when (mode) {
            Mode.DRIVER_CONTROLLED -> {
                if (gamepad1.triangle) {
                    mode = Mode.AUTOMATED
                }
                if(gamepadEvent1.leftStickButton())
                    slowMode = !slowMode

                drive.setWeightedDrivePower(
                    Pose2d(
                        if (slowMode) -gamepad1.left_stick_y.toDouble() / 3 else -gamepad1.left_stick_y.toDouble(),
                        if (slowMode) -gamepad1.left_stick_x.toDouble() / 3 else -gamepad1.left_stick_x.toDouble(),
                        if (slowMode) -gamepad1.right_stick_x.toDouble() / 3 else -gamepad1.right_stick_x.toDouble()
                    )
                )

                intake.update(listOf(
                    gamepad1.left_trigger > 0.5, // Hold to intake
                    gamepad1.dpad_down, // First level with arm
                    gamepadEvent1.leftBumper() // Toggle claw
                ))

                lift.update(listOf(
                    gamepadEvent2.dPadLeft(), // Idle
                    gamepadEvent2.dPadRight(), // Second level
                    gamepadEvent2.dPadUp(), // Third level
                    gamepad1.right_bumper // Deposit
                ))
            }

            Mode.AUTOMATED -> {
                if (gamepad1.circle) {
                    mode = Mode.DRIVER_CONTROLLED
                }
                cycle()
            }
        }

        telemetry.addData("Mode", mode)
        telemetry.addData("Extension State", intake.extensionState)
        telemetry.addData("Claw State", intake.clawState)
        telemetry.addData("Depositor State", lift.depositorState)
        telemetry.update()
    }

    private fun cycle() {
        if (gamepad1.circle) {
            mode = Mode.DRIVER_CONTROLLED
        }
        intake.updateExtensionState(Intake.ExtensionState.EXTENDING)
        intake.closeClaw()
        update(200)
        intake.updateExtensionState(Intake.ExtensionState.IDLE)
        update(1000)
        intake.openClaw()
        update(500)
        lift.setLiftPosition(ActuationConstants.LiftConstants.liftPositions[2])
        intake.updateExtensionState(Intake.ExtensionState.EXTENDING)
        update(1000)
        lift.updateDepositorState(Lift.DepositorState.UP)
        update(500)
        lift.updateDepositorState(Lift.DepositorState.DOWN)
        lift.setLiftPosition(ActuationConstants.LiftConstants.liftPositions[0])
        update(1000)
    }

    private fun update(time: Long) {
        timer.reset()
        while(timer.milliseconds() <= time) {
            telemetry.addData("Circle Detected", gamepad1.circle)
            if (gamepad1.circle) {
                mode = Mode.DRIVER_CONTROLLED
            }
            intake.update()
            lift.update()
        }
    }

    enum class Mode {
        DRIVER_CONTROLLED,
        AUTOMATED
    }
}