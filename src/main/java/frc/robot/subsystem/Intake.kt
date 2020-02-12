package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.OI
import frc.robot.Robotmap

object Intake {
    private val intakeVictor = VictorSPX(Robotmap.intakeVictor)
    private val intakeSwitchSensor = DigitalInput(9)

    fun intakeRun(a: Double){
        intakeVictor.set(ControlMode.PercentOutput, a)
    }

    fun getLimSwitch(): Boolean{
        return intakeSwitchSensor.get()
    }
}