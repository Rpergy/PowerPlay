package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {
    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(75, 190, 350)
    }

    @Config
    object DepositorConstants {
        @JvmField var targetPosition = 0.0
        const val DOWN = 0.0
        const val UP = 0.13
    }

    @Config
    object ExtensionConstants {
        @JvmField var targetPosition = 0.47
        const val RETRACTED = 0.465
        const val TRANSFER = 0.483
        const val EXTENDED = 0.529
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.525
        const val IDLE = 0.53
        const val TRANSFER = 0.537
        const val DOWN = 0.453
        const val FIRST_JUNCTION = 0.495
    }

    @Config
    object ClawConstants {
        @JvmField var targetPosition = 0.66
        const val INIT = 0.69
        const val OPEN = 0.66
        const val CLOSED = 0.62
    }
}
