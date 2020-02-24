package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.Constants
import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Roller {
    private val rollerVictor = VictorSPX(Robotmap.rollerVictor)
    private val rollerLimSwitch = DigitalInput(Constants.rollerLimSwitchID)

    fun rollerFollow(){
        rollerVictor.set(ControlMode.Follower, Robotmap.conveyorTalon.toDouble())
    }

    fun stop(){
        rollerVictor.set(ControlMode.PercentOutput, 0.0)
    }

    fun getLimSwitch(): Boolean{
        return rollerLimSwitch.get()
    }
}