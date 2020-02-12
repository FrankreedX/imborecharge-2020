package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton
import kotlin.math.abs

object OI {
    private fun deadZone(inp: Double): Double{
        return if (abs(inp) < 0.15) 0.0 else inp
    }

    private val driverController = Joystick(0)

    val throttle
        get() = deadZone(driverController.getRawAxis(1)*abs(driverController.getRawAxis(1)))

    val turn
        get() = deadZone(driverController.getRawAxis(2))

    val leftTrigger
        get() = deadZone(driverController.getRawAxis(3))

    val rightTrigger
        get() = deadZone(driverController.getRawAxis(4))

    val quickTurn: Boolean
        get() = (leftTrigger + rightTrigger) > 0

    val driving: Boolean
        get() = (throttle + turn + leftTrigger + rightTrigger) > 0

    private val manipulatorController = Joystick(1)

    val bigStick
        get() = deadZone(manipulatorController.getRawAxis(1))

    val flipflap: Boolean
        get() = manipulatorController.getRawAxis(3) < 0.0

    val hat
        get() = if(manipulatorController.pov == 0) 1.0 else if (manipulatorController.pov == 180) -1.0 else 0.0

    val conveyorAuto
        get() = manipulatorController.getRawButton(4)

    val shooterAuto
        get() = manipulatorController.getRawButton(9)

    val shooterManual
        get() = manipulatorController.getRawButton(10)

    val FireSequenceButton
        get() = manipulatorController.getRawButtonPressed(11)

    val randomButton
        get() = manipulatorController.getRawButtonPressed(12)
}