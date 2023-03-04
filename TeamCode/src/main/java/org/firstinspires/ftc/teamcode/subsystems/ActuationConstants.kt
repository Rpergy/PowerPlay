package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {
    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(
            64, // Transfer preset
            68, // Idle preset
            180, // Medium junction preset
            330 // High junction preset
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
        @JvmField var targetPosition = 0.463
        const val RETRACTED = 0.463
        const val TRANSFER = 0.483
        const val EXTENDED = 0.529
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.529
        val coneStackPositions = arrayListOf(
            0.467,
            0.463,
            0.459,
            0.454,
            0.450
        )
        const val IDLE = 0.529
        const val TRANSFER = 0.534
        const val DOWN = 0.452
        const val FIRST_JUNCTION = 0.495
    }

    @Config
    object ClawConstants {
        @JvmField var targetPosition = 0.66
        const val INIT = 0.68
        const val OPEN = 0.665
        const val CLOSED = 0.62
    }
}
