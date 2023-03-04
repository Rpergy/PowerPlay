package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.util.GamepadEventPS

@TeleOp(name = "RumbleOp")
class RumbleOp: OpMode() {
    private lateinit var gamepadEvent: GamepadEventPS

    override fun init() {
        gamepadEvent = GamepadEventPS(gamepad1)
    }

    override fun loop() {
        if (gamepadEvent.triangle())
            gamepad1.rumble(500)
    }
}