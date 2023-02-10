package org.firstinspires.ftc.teamcode.subsystems

class ActuationConstants {
    object LiftConstants {
        val LIFT_POSITIONS = arrayListOf(750, 1800, 3400)
        const val TICKS_IN_DEGREES = 537.6 / 180
        const val P = 0.005
        const val I = 0.0
        const val D = 0.0
        const val F = 0.1
    }

    object DepositorConstants {
        const val DOWN = 0.0
        const val UP = 0.1
    }

    object ExtensionConstants {
        const val RETRACTED_RIGHT = 0.4
        const val RETRACTED_LEFT = 0.36
        const val EXTENDED_RIGHT = 0.54
        const val EXTENDED_LEFT = 0.51
    }

    object ArmConstants {
        const val IDLE = 0.305
        const val INTAKING = 0.228
        const val DEPOSITING = 0.26
    }

    object ClawConstants {
        const val OPEN = 0.67
        const val CLOSED = 0.57
    }
}