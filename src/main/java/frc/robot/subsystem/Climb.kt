package frc.robot.subsystem

import com.revrobotics.*
import frc.robot.Constants
import frc.robot.Robotmap

object Climb {
    private val climbSpark = CANSparkMax(Robotmap.winchSpark, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val climbPid = CANPIDController(climbSpark)

    private const val kP = Constants.climbKP
    private const val kI = Constants.climbKI
    private const val kD = Constants.climbKD
    private const val kFF = Constants.climbKFF
    private const val kMaxOutput = Constants.climbKMaxOutput
    private const val kMinOutput = Constants.climbKMinOutput
    private const val maxVel = Constants.climbMaxVel
    private const val maxAcc = Constants.climbMaxAcc

    init {
        climbSpark.restoreFactoryDefaults()

        climbPid.p = kP
        climbPid.i = kI
        climbPid.d = kD
        climbPid.ff = kFF
        climbPid.setOutputRange(kMinOutput, kMaxOutput)

        val smartMotionSlot = 0
        climbPid.setSmartMotionMaxVelocity(maxVel, smartMotionSlot)
        climbPid.setSmartMotionMaxAccel(maxAcc, smartMotionSlot)
    }

    fun runClimb(setPoint: Double){
        climbPid.setReference(setPoint, ControlType.kSmartMotion)
    }
}