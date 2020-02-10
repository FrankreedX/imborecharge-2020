package frc.robot.subsystem

import com.revrobotics.*
import frc.robot.Constants
import frc.robot.Robotmap

object Climb {
    private val climbSpark = CANSparkMax(Robotmap.winchSpark, CANSparkMaxLowLevel.MotorType.kBrushless)

    init {
        climbSpark.restoreFactoryDefaults()
    }

    fun runClimb(setPoint: Double){
        climbSpark.set(setPoint)
    }
}