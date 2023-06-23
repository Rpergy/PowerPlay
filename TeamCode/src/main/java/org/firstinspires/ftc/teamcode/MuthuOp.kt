package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.outoftheboxrobotics.photoncore.PhotonCore
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.util.GamepadEventPS
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
    private var fieldCentric = false
    private var slowMode = false

    override fun init() {
        drive = SampleMecanumDrive(hardwareMap)
        intake = Intake(hardwareMap)
        lift = Lift(hardwareMap)

        gamepadEvent1 =
            GamepadEventPS(gamepad1)
        gamepadEvent2 =
            GamepadEventPS(gamepad2)

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

                if(gamepadEvent1.leftStickButton())
                    slowMode = !slowMode

                if (gamepadEvent1.rightStickButton())
                    fieldCentric = !fieldCentric

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
                    gamepad1.right_trigger > 0.5, // Hold to intake
                    gamepad2.dpad_down, // Low junction
                    gamepad1.left_trigger > 0.5, // Lower arm
                    gamepad2.triangle, // Cone stack top cone
                    gamepad2.circle, // Cone stack second cone
                    gamepad2.cross, // Cone stack third cone
                    gamepad2.square, // Cone stack fourth cone
                    gamepadEvent1.leftBumper() || gamepadEvent2.leftBumper() // Toggle claw
                ))

                lift.update(listOf(
                    gamepadEvent2.dPadLeft(), // Idle
                    gamepadEvent2.dPadRight(), // Medium junction
                    gamepadEvent2.dPadUp(), // High junction
                    gamepad2.left_trigger > 0.5, // Manually lower lift
                    gamepad2.right_trigger > 0.5, // Manually raise lift
                    gamepad1.right_bumper || gamepad2.right_bumper // Deposit
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

        telemetry.addData("Mode", mode)
        telemetry.addData("Drive Mode", if (fieldCentric) "Field Centric" else "Robot Centric")
        telemetry.addData("Slow Mode", slowMode)
        telemetry.addData("Extension State", intake.extensionState)
        telemetry.addData("Claw State", intake.clawState)
        telemetry.addData("Depositor State", lift.depositorState)
        telemetry.update()
    }

    private fun cycle() {
        when (timer.milliseconds()) {
            in 0.0 .. 600.0 -> intake.updateExtensionState(Intake.ExtensionState.EXTENDING)
            in 600.0 .. 800.0 -> intake.updateClawState(Intake.ClawState.CLOSED)
            in 800.0 .. 1800.0 -> intake.updateExtensionState(Intake.ExtensionState.TRANSFERRING)
            in 1800.0 ..  2200.0 -> intake.updateClawState(Intake.ClawState.OPEN)
            in 2200.0 .. 2700.0 -> lift.setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[3])
            in 2700.0 .. 3000.0 -> lift.updateDepositorState(Lift.DepositorState.UP)
            in 3000.0 .. 3200.0 -> intake.updateExtensionState(Intake.ExtensionState.EXTENDING)
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
