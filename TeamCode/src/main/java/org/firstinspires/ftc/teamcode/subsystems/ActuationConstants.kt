package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {
    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(
            38, // Transfer preset
            48, // Idle preset
            180, // Medium junction preset
            320 // High junction preset
        )
    }

    @Config
    object DepositorConstants {
        @JvmField var targetPosition = 0.0
        const val DOWN = 0.0
        const val UP = 0.13
    }

    @Config
    object ExtensionConstants {
        @JvmField var targetPosition = 0.45
        const val RETRACTED = 0.05
        const val TRANSFER = 0.14
        const val EXTENDED = 0.41
        const val FULLY_EXTENDED = 0.48
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.27
        val coneStackPositions = arrayListOf(
            0.84,
            0.87,
            0.9,
            0.93,
            0.96
        )
        const val IDLE = 0.22
        const val TRANSFER = 0.19
        const val DOWN = 0.95
        const val FIRST_JUNCTION = 0.53
    }

    @Config
    object ClawConstants {
        @JvmField var targetPosition = 0.66
        const val INIT = 0.65
        const val OPEN = 0.64
        const val CLOSED = 0.58
    }
}
