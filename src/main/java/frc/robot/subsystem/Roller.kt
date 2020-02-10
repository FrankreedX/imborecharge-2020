package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Roller {
    private val rollerTalon = TalonWrapper(Robotmap.rollerTalon)
    private val rollerLimSwitch = DigitalInput(3).get()

    fun rollerFollow(){
        rollerTalon.set(ControlMode.Follower, Robotmap.conveyorTalon.toDouble())
    }

    fun stop(){
        rollerTalon.set(0.0)
    }

    fun getLimSwitch(): Boolean{
        return rollerLimSwitch
    }
}