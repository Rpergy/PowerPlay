package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d

class FieldConstants {
    object LeftBlueAutonomous {
        val startPosition = Pose2d(38.0, 64.0, Math.toRadians(-90.0))
        val cyclePosition1 = Vector2d(36.0, 15.0)
        val cyclePosition2 = Pose2d(42.5, -6.5, Math.toRadians(-168.0))
        val parkingTransition = Pose2d(40.0, 0.0, Math.toRadians(-90.0))
        val parkPosition1 = Pose2d(80.0, 2.0, Math.toRadians(-90.0))
        val parkPosition2 = Pose2d(40.0, 2.0, Math.toRadians(-90.0))
        val parkPosition3 = Pose2d(5.0, 2.0, Math.toRadians(-90.0))
    }

    object LeftRedAutonomous {
        val startPosition = Pose2d(-38.0, -64.0, Math.toRadians(90.0))
        val cyclePosition1 = Vector2d(-36.0, -20.0)
        val cyclePosition2 = Pose2d(-40.5, -3.5, Math.toRadians(14.0))
        val parkingTransition = Pose2d(-36.0, -15.0, Math.toRadians(90.0))
        val parkPosition1 = Pose2d(-70.0, -14.0, Math.toRadians(90.0))
        val parkPosition2 = Pose2d(-38.0, -14.0, Math.toRadians(90.0))
        val parkPosition3 = Pose2d(-10.0, -14.0, Math.toRadians(90.0))
    }

    object RightBlueAutonomous {
        val startPosition = Pose2d(-38.0, 64.0, Math.toRadians(-90.0))
        val cyclePosition1 = Vector2d(-36.0, 20.0)
        val cyclePosition2 = Pose2d(-39.5, 3.5, Math.toRadians(-15.0))
        val parkingTransition = Pose2d(-36.0, 15.0, Math.toRadians(-90.0))
        val parkPosition1 = Pose2d(-10.0, 15.0, Math.toRadians(-90.0))
        val parkPosition2 = Pose2d(-35.0, 15.0, Math.toRadians(-90.0))
        val parkPosition3 = Pose2d(-70.0, 15.0, Math.toRadians(-90.0))
    }

    object RightRedAutonomous {
        val startPosition = Pose2d(38.0, -64.0, Math.toRadians(90.0))
        val cyclePosition1 = Vector2d(36.0, -17.0)
        val cyclePosition2 = Pose2d(44.0, 8.0, Math.toRadians(167.0))
        val parkingTransition = Pose2d(40.0, 0.0, Math.toRadians(90.0))
        val parkPosition1 = Pose2d(2.0, -2.0, Math.toRadians(90.0))
        val parkPosition2 = Pose2d(40.0, -2.0, Math.toRadians(90.0))
        val parkPosition3 = Pose2d(80.0, -2.0, Math.toRadians(90.0))
    }
}