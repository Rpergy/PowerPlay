package org.firstinspires.ftc.teamcode.hardware

class ActuationConstants {
    object LiftConstants {
        val liftPositions = arrayListOf(700, 1800, 3400)
        const val tickInDegrees = 537.6 / 180
        const val p = 0.005
        const val i = 0.0
        const val d = 0.0
        const val f = 0.1
    }

    object DepositorConstants {
        const val DOWN = 0.0
        const val UP = 0.1
    }

    object ExtensionConstants {
        const val RETRACTED = 0.36
        const val EXTENDED = 0.5
    }

    object ArmConstants {
        const val IDLE = 0.295
        const val INTAKING = 0.224
        const val DEPOSITING = 0.26
    }

    object ClawConstants {
        const val OPEN = 0.67
        const val CLOSED = 0.57
    }
}