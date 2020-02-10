package frc.robot.subsystem

import com.revrobotics.*
import frc.robot.Constants
import frc.robot.Robotmap

object Shooter {
    private val shooterSpark = CANSparkMax(Robotmap.shooterSpark, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val shooterPid = CANPIDController(shooterSpark)
    private val shooterEncoder = CANEncoder(shooterSpark)
    private const val shooterIdleSpeed = Constants.shooterIdleSpeed

    private const val kP = Constants.shooterKP
    private const val kI = Constants.shooterKI
    private const val kD = Constants.shooterKD
    private const val kFF = Constants.shooterKFF
    private const val kMaxOutput = Constants.shooterKMaxOutput
    private const val kMinOutput = Constants.shooterKMinOutput
    private const val maxVel = Constants.shooterMaxVel
    private const val maxAcc = Constants.shooterMaxAcc

    init {
        shooterSpark.restoreFactoryDefaults()
        shooterSpark.idleMode = CANSparkMax.IdleMode.kCoast

        shooterPid.p = kP
        shooterPid.i = kI
        shooterPid.d = kD
        shooterPid.ff = kFF
        shooterPid.setOutputRange(kMinOutput, kMaxOutput)
    }

    fun runShooter(setPoint: Double){
        if (setPoint < 0.0) shooterPid.setReference(shooterIdleSpeed, ControlType.kVelocity)
        shooterPid.setReference(setPoint* Constants.neoMaxRPM, ControlType.kVelocity)
    }

    fun stop(){
        shooterPid.setReference(0.0,ControlType.kVoltage)
    }

    fun getSpeed(): Double{
        return shooterEncoder.velocity
    }
}