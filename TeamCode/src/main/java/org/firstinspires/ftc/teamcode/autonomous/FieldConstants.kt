package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d

class FieldConstants {
    object LeftBlueAutonomous {
        val startPosition = Pose2d(38.0, 64.0, Math.toRadians(-90.0))
        val cyclePosition1 = Vector2d(36.0, 15.0)
        val cyclePosition2 = Pose2d(38.0, 0.0, Math.toRadians(-160.0))
        val parkPosition1 = Pose2d(60.0, 10.0, Math.toRadians(-90.0))
        val parkPosition2 = Pose2d(40.0, 10.0, Math.toRadians(-90.0))
        val parkPosition3 = Pose2d(5.0, 10.0, Math.toRadians(-90.0))
    }

    object RightRedAutonomous {
        val startPosition = Pose2d(38.0, -64.0, Math.toRadians(90.0))
        val cyclePosition1 = Vector2d(36.0, -17.0)
        val cyclePosition2 = Pose2d(42.0, 2.0, Math.toRadians(170.0))
        val parkPosition1 = Pose2d(60.0, -10.0, Math.toRadians(90.0))
        val parkPosition2 = Pose2d(40.0, -10.0, Math.toRadians(90.0))
        val parkPosition3 = Pose2d(5.0, -10.0, Math.toRadians(90.0))
    }
} /* make
        faster
        */