package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Intake {
    private val intakeTalon = TalonWrapper(Robotmap.intakeTalon)
    private val intakeLimSwitch = DigitalInput(1).get()

    fun intakeRun(a: Double){
        intakeTalon.set(a)
    }

    fun follow(){
        intakeTalon.set(ControlMode.Follower, Robotmap.conveyorTalon.toDouble())
    }

    fun getLimSwitch(): Boolean{
        return intakeLimSwitch
    }
}