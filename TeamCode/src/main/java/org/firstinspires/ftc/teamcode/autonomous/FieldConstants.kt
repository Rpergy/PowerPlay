package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d

class FieldConstants {
    object LeftBlueAutonomous {
        val startPosition = Pose2d(38.0, 64.0, Math.toRadians(-90.0))
        val cyclePosition1 = Vector2d(39.0, 10.0)
        val cyclePosition2 = Pose2d(37.4, 6.0, Math.toRadians(-167.5))
        val parkingTransition = Pose2d(38.0, 14.0, Math.toRadians(-90.0))
        val parkPosition1 = Pose2d(72.0, 15.0, Math.toRadians(-90.0))
        val parkPosition2 = Pose2d(38.0, 15.0, Math.toRadians(-90.0))
        val parkPosition3 = Pose2d(13.0, 15.0, Math.toRadians(-90.0))
    }

    object LeftRedAutonomous {
        val startPosition = Pose2d(-38.0, -64.0, Math.toRadians(90.0))
        val cyclePosition1 = Vector2d(-37.0, -20.0)
        val cyclePosition2 = Pose2d(-38.0, -5.0, Math.toRadians(15.0))
        val parkingTransition = Pose2d(-39.0, -15.0, Math.toRadians(90.0))
        val parkPosition1 = Pose2d(-70.0, -14.0, Math.toRadians(90.0))
        val parkPosition2 = Pose2d(-41.0, -14.0, Math.toRadians(90.0))
        val parkPosition3 = Pose2d(-10.0, -14.0, Math.toRadians(90.0))
    }

    object RightBlueAutonomous {
        val startPosition = Pose2d(-38.0, 64.0, Math.toRadians(-90.0))
        val cyclePosition1 = Vector2d(-37.0, 20.0)
        val cyclePosition2 = Pose2d(-38.0, 4.0, Math.toRadians(-17.0))
        val parkingTransition = Pose2d(-36.0, 15.0, Math.toRadians(-90.0))
        val parkPosition1 = Pose2d(-10.0, 15.0, Math.toRadians(-90.0))
        val parkPosition2 = Pose2d(-35.0, 15.0, Math.toRadians(-90.0))
        val parkPosition3 = Pose2d(-70.0, 15.0, Math.toRadians(-90.0))
    }

    object RightRedAutonomous {
        val startPosition = Pose2d(38.0, -64.0, Math.toRadians(90.0))
        val cyclePosition1 = Vector2d(38.0, -20.0)
        val cyclePosition2 = Pose2d(38.0, -4.5, Math.toRadians(163.0))
        val parkingTransition = Pose2d(35.0, -16.0, Math.toRadians(90.0))
        val parkPosition1 = Pose2d(10.0, -14.0, Math.toRadians(90.0))
        val parkPosition2 = Pose2d(38.0, -14.0, Math.toRadians(90.0))
        val parkPosition3 = Pose2d(70.0, -14.0, Math.toRadians(90.0))
    }
}