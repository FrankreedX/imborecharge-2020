package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Roller {
    private val rollerTalon = TalonWrapper(Robotmap.rollerTalon)

    fun rollerFollow(){
        rollerTalon.set(ControlMode.Follower, Robotmap.conveyorTalon.toDouble())
    }

    fun stop(){
        rollerTalon.set(0.0)
    }
}