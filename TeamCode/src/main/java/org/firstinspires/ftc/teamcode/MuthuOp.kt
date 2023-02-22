package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.outoftheboxrobotics.photoncore.PhotonCore
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.ActuationConstants
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Lift

@TeleOp(name = "MuthuOp\uD83D\uDC4C\uD83D\uDC4C\uD83D\uDE0D\uD83C\uDFB6\uD83C\uDFB6\uD83D\uDE0E\uD83D\uDE1C\uD83D\uDE2D\uD83E\uDD70\uD83D\uDE08\uD83D\uDC7A\uD83D\uDC7A\uD83E\uDD23\uD83E\uDD23\uD83D\uDE15\uD83D\uDE1C\uD83D\uDE2D\uD83E\uDD70\uD83E\uDD70\uD83D\uDE18\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD\uFDFD")
class MuthuOp: OpMode() {
    private lateinit var drive: SampleMecanumDrive
    private lateinit var intake: Intake
    private lateinit var lift: Lift
    private lateinit var gamepadEvent1: GamepadEventPS
    private lateinit var gamepadEvent2: GamepadEventPS
    private lateinit var runtime: ElapsedTime
    private lateinit var timer: ElapsedTime

    private var mode = Mode.DRIVER_CONTROLLED
    private var fieldCentric = false
    private var slowMode = false

    override fun init() {
        drive = SampleMecanumDrive(hardwareMap)
        intake = Intake(hardwareMap)
        lift = Lift(hardwareMap)
        gamepadEvent1 = GamepadEventPS(gamepad1)
        gamepadEvent2 = GamepadEventPS(gamepad2)
        runtime = ElapsedTime()
        timer = ElapsedTime()

        telemetry.addLine("Initialized!")
        telemetry.update()
    }

    override fun loop() {
        PhotonCore.enable()
        drive.update()

        when (mode) {
            Mode.DRIVER_CONTROLLED -> {
                if (gamepadEvent1.triangle())
                    mode = Mode.AUTOMATED

                if (gamepadEvent1.leftStickButton())
                    fieldCentric = !fieldCentric

                if(gamepadEvent1.rightStickButton())
                    slowMode = !slowMode

                if (fieldCentric) {
                    val input = Vector2d (
                        if (slowMode) -gamepad1.left_stick_y.toDouble() / 3 else -gamepad1.left_stick_y.toDouble(),
                        if (slowMode) -gamepad1.left_stick_x.toDouble() / 3 else -gamepad1.left_stick_x.toDouble()
                    ).rotated(-drive.poseEstimate.heading)

                    drive.setWeightedDrivePower (
                        Pose2d (
                            input.x,
                            input.y,
                            if (slowMode) -gamepad1.right_stick_x.toDouble() / 3 else -gamepad1.right_stick_x.toDouble()
                        )
                    )
                } else {
                    drive.setWeightedDrivePower (
                        Pose2d (
                            if (slowMode) -gamepad1.left_stick_y.toDouble() / 3 else -gamepad1.left_stick_y.toDouble(),
                            if (slowMode) -gamepad1.left_stick_x.toDouble() / 3 else -gamepad1.left_stick_x.toDouble(),
                            if (slowMode) -gamepad1.right_stick_x.toDouble() / 3 else -gamepad1.right_stick_x.toDouble()
                        )
                    )
                }

                intake.update(listOf(
                    gamepad1.left_trigger > 0.5, // Hold to intake
                    gamepad2.dpad_down, // First level
                    gamepad1.right_trigger > 0.5, // Lower arm
                    gamepadEvent1.leftBumper() // Toggle claw
                ))

                lift.update(listOf(
                    gamepadEvent2.dPadLeft(), // Idle
                    gamepadEvent2.dPadRight(), // Second level
                    gamepadEvent2.dPadUp(), // Third level
                    gamepad1.right_bumper // Deposit
                ))

                timer.reset()
            }

            Mode.AUTOMATED -> {
                if (gamepadEvent1.triangle() ) {
                    mode = Mode.DRIVER_CONTROLLED
                }
                cycle()
            }
        }

        if (runtime.seconds() == 110.0)
            gamepad1.rumble(500)

        telemetry.addData("Heading", drive.poseEstimate.heading)
        telemetry.addData("Mode", mode)
        telemetry.addData("Drive Mode", if (fieldCentric) "Field Centric" else "Robot Centric")
        telemetry.addData("Extension State", intake.extensionState)
        telemetry.addData("Claw State", intake.clawState)
        telemetry.addData("Depositor State", lift.depositorState)
        telemetry.update()
    }

    private fun cycle() {
        when (timer.milliseconds()) {
            in 0.0 .. 500.0 -> intake.updateExtensionState(Intake.ExtensionState.EXTENDING)
            in 500.0 .. 700.0 -> intake.updateClawState(Intake.ClawState.CLOSED)
            in 700.0 .. 1700.0 -> intake.updateExtensionState(Intake.ExtensionState.IDLE)
            in 1700.0 .. 2100.0 -> intake.updateClawState(Intake.ClawState.OPEN)
            in 2100.0 .. 2600.0 -> lift.setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[2])
            in 2600.0 .. 2900.0 -> lift.updateDepositorState(Lift.DepositorState.UP)
            in 2900.0 .. 3100.0 -> intake.updateExtensionState(Intake.ExtensionState.EXTENDING)
            else -> {
                lift.updateDepositorState(Lift.DepositorState.DOWN)
                lift.setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[0])
                timer.reset()
            }
        }
    }

    enum class Mode {
        DRIVER_CONTROLLED,
        AUTOMATED
    }
}