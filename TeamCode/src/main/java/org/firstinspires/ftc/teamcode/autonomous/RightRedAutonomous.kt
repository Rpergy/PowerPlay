package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.cv.AprilTagDetectionPipeline
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.ActuationConstants
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.openftc.apriltag.AprilTagDetection
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import java.util.*

@Autonomous(name = "Right Red Autonomous")
class RightRedAutonomous: LinearOpMode() {
    private val fx = 578.272
    private val fy = 578.272
    private val cx = 402.145
    private val cy = 221.506
    private val tagsize = 0.166
    private var tagId = 2
    private lateinit var drive: SampleMecanumDrive
    private lateinit var intake: Intake
    private lateinit var lift: Lift

    override fun runOpMode() {
        drive = SampleMecanumDrive(hardwareMap)
        drive.poseEstimate = FieldConstants.RightRedAutonomous.startPosition
        intake = Intake(hardwareMap)
        lift = Lift(hardwareMap)

        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier(
            "cameraMonitorViewId",
            "id",
            hardwareMap.appContext.packageName
        )

        val camera = OpenCvCameraFactory.getInstance().createWebcam(
            hardwareMap.get(
                WebcamName::class.java, "Webcam 1"
            ), cameraMonitorViewId
        )

        val pipeline = AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy)

        camera.setPipeline(pipeline)

        camera.openCameraDeviceAsync(object : OpenCvCamera.AsyncCameraOpenListener {
            override fun onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT)
            }

            override fun onError(errorCode: Int) {}
        })

        FtcDashboard.getInstance().startCameraStream(camera, 0.0)

        while (!isStarted && !isStopRequested) {
            val currentDetections: ArrayList<AprilTagDetection> = pipeline.latestDetections

            if (currentDetections.size != 0) {
                when (currentDetections[0].id) {
                    11 -> tagId = 1
                    15 -> tagId = 3
                }
            }

            telemetry.addData("Tag ID", tagId)
            telemetry.update()
            sleep(20)
        }

        lift.setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[0])

        val toCyclePosition = drive.trajectorySequenceBuilder(drive.poseEstimate)
            .lineTo(FieldConstants.RightRedAutonomous.cyclePosition1)
            .lineToLinearHeading(FieldConstants.RightRedAutonomous.cyclePosition2)
            .build()

        drive.followTrajectorySequence(toCyclePosition)

        for (i in 0..5) {
            lift.setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[3])
            Thread.sleep(500)
            lift.updateDepositorState(Lift.DepositorState.UP)
            Thread.sleep(500)
            lift.updateDepositorState(Lift.DepositorState.DOWN)
            lift.setLiftPosition(ActuationConstants.LiftConstants.LIFT_POSITIONS[0])
            if (i != 5) {
                intake.updateExtensionState(Intake.ExtensionState.EXTENDING,
                    fullyExtended = true,
                    bind = false,
                    armPosition = ActuationConstants.ArmConstants.coneStackPositions[i]
                )
                Thread.sleep(800)
                intake.updateClawState(Intake.ClawState.CLOSED)
                Thread.sleep(300)
                intake.updateExtensionState(Intake.ExtensionState.TRANSFERRING)
                Thread.sleep(1000)
                intake.updateClawState(Intake.ClawState.OPEN)
                Thread.sleep(400)
            } else {
                intake.updateExtensionState(Intake.ExtensionState.IDLE)
                Thread.sleep(500)
            }
        }

        val toPark = drive.trajectorySequenceBuilder(toCyclePosition.end())
            .setVelConstraint(MinVelocityConstraint(listOf(
                AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL * 2),
                MecanumVelocityConstraint(DriveConstants.MAX_VEL * 2, DriveConstants.TRACK_WIDTH)
            )))

            .lineToLinearHeading(FieldConstants.RightRedAutonomous.parkingTransition)
            .lineToLinearHeading(
                when (tagId) {
                    1 -> FieldConstants.RightRedAutonomous.parkPosition1
                    2 -> FieldConstants.RightRedAutonomous.parkPosition2
                    3 -> FieldConstants.RightRedAutonomous.parkPosition3
                    else -> FieldConstants.RightRedAutonomous.parkPosition2
                })
            .build()

        drive.followTrajectorySequence(toPark)
    }
}
