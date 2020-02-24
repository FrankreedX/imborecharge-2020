package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import com.revrobotics.*
import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.Constants
import frc.robot.Robotmap
import kotlin.math.abs

object Climb {
    private val climbSpark = CANSparkMax(Robotmap.winchSpark, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val climbPid = CANPIDController(climbSpark)
    private val climbEncoder = CANEncoder(climbSpark)
    private val climbLimSwitch = DigitalInput(3)

    private const val kP = Constants.shooterKP

    init {
        climbSpark.restoreFactoryDefaults()
        climbPid.p = kP
    }

    fun lower(setPoint: Double){
        if (setPoint > 0.0)
        climbSpark.setVoltage(abs(setPoint*12))
    }

    fun raise(setPoint: Double){
        if (setPoint < 0.0)
            climbSpark.setVoltage(abs(setPoint*12))
    }

    fun getLimSwitch(): Boolean{
        return climbLimSwitch.get()
    }
}