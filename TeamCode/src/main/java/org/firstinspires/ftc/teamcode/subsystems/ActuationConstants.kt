package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {
    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(50, 190, 330)
    }

    @Config
    object DepositorConstants {
        @JvmField var targetPosition = 0.0
        const val DOWN = 0.01
        const val UP = 0.13
    }

    @Config
    object ExtensionConstants {
        @JvmField var targetPosition = 0.42
        const val RETRACTED = 0.48
        const val EXTENDED = 0.54
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.525
        const val IDLE = 0.521
        const val DOWN = 0.458
        const val FIRST_JUNCTION = 0.488
    }

    @Config
    object ClawConstants {
        @JvmField var targetPosition = 0.66
        const val OPEN = 0.66
        const val CLOSED = 0.62
    }
}