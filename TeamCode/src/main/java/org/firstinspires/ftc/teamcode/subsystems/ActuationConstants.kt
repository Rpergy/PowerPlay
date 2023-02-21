package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {

    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(50, 190, 350)
    }

    @Config
    object DepositorConstants {
        @JvmField var targetPosition = 0.0
        const val DOWN = 0.021
        const val UP = 0.13
    }

    @Config
    object ExtensionConstants {
        @JvmField var targetPosition = 0.6
        const val RETRACTED_RIGHT = 0.48
        const val RETRACTED_LEFT = 0.48
        const val EXTENDED_RIGHT = 0.65
        const val EXTENDED_LEFT = 0.58
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.525
        const val IDLE = 0.5
        const val DOWN = 0.345
        const val FIRST_JUNCTION = 0.4
    }

    @Config
    object ClawConstants {
        @JvmField var targetPosition = 0.66
        const val OPEN = 0.66
        const val CLOSED = 0.63
    }
}