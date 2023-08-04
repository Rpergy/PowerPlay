package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config

class ActuationConstants {
    @Config
    object LiftConstants {
        @JvmField var targetPosition = 0
        val LIFT_POSITIONS = arrayListOf(
            44, // Transfer preset
            50, // Idle preset
            185, // Medium junction preset
            325 // High junction preset
        )//this is omkar
    }

    @Config//finite state machine
    object DepositorConstants {
        @JvmField var targetPosition = 0.0
        const val DOWN = 0.0//modular progrsmming!!!
        const val UP = 0.13
    }

    @Config
    object ExtensionConstants {
        @JvmField var targetPosition = 0.45
        const val RETRACTED = 0.1
        const val TRANSFER = 0.2
        const val EXTENDED = 0.5
        const val FULLY_EXTENDED = 0.57
    }

    @Config
    object ArmConstants {
        @JvmField var targetPosition = 0.27
        val coneStackPositions = arrayListOf(
            0.77,
            0.81,
            0.86,
            0.89,
            0.92
        )
        const val IDLE = 0.22
        const val TRANSFER = 0.19
        const val DOWN = 0.92
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
