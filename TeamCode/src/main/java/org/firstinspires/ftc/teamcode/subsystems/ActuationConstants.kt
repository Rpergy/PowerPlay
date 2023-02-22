package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {
    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(70, 190, 330)
    }

    @Config
    object DepositorConstants {
        @JvmField var targetPosition = 0.0
        const val DOWN = 0.0
        const val UP = 0.13
    }

    @Config
    object ExtensionConstants {
        @JvmField var targetPosition = 0.42
        const val RETRACTED = 0.475
        const val TRANSFER = 0.482
        const val EXTENDED = 0.53
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.525
        const val IDLE = 0.531
        const val TRANSFER = 0.535
        const val DOWN = 0.456
        const val FIRST_JUNCTION = 0.495
    }

    @Config
    object ClawConstants {
        @JvmField var targetPosition = 0.66
        const val OPEN = 0.66
        const val CLOSED = 0.62
    }
}