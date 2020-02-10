package frc.robot.subsystem

import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Intake {
    private val intakeTalon = TalonWrapper(Robotmap.intakeTalon)
    private val intakeLimSwitch = DigitalInput(1).get()

    fun intakeRun(a: Double){
        intakeTalon.set(a)
    }

    fun getLimSwitch(): Boolean{
        return intakeLimSwitch
    }
}