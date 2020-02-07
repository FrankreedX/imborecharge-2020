package frc.robot.subsystem

import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Intake {
    private val intakeTalon = TalonWrapper(Robotmap.intakeTalon)

    fun intakeRun(a: Double){
        intakeTalon.set(a)
    }
}