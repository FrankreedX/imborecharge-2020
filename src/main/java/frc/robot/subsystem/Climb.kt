package frc.robot.subsystem

import com.revrobotics.*
import frc.robot.Robotmap
import kotlin.math.abs

object Climb {
    private val climbSpark = CANSparkMax(Robotmap.winchSpark, CANSparkMaxLowLevel.MotorType.kBrushless)

    init {
        climbSpark.restoreFactoryDefaults()
    }

    fun runClimb(setPoint: Double){
        climbSpark.setVoltage(abs(setPoint)*12.0)
    }
}