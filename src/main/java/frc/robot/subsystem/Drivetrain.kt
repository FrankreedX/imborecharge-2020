package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.NeutralMode
import frc.robot.Robotmap
import frc.robot.engine.DriveSignal
import frc.robot.engine.TalonWrapper

object Drivetrain {
    private val leftMaster = TalonWrapper(Robotmap.masterTalonLeft)
    private val rightMaster = TalonWrapper(Robotmap.masterTalonRight)
    private val leftSlave = TalonWrapper(Robotmap.slaveTalonLeft)
    private val rightSlave = TalonWrapper(Robotmap.slaveTalonRight)

    init {
        leftMaster.apply {
            setNeutralMode(NeutralMode.Brake)
            setMagEncoder()
            configFactoryDefault()
        }

        rightMaster.apply {
            setNeutralMode(NeutralMode.Brake)
            setMagEncoder()
            configFactoryDefault()
            inverted = true
        }

        leftSlave.apply {
            follow(leftMaster)
            setNeutralMode(NeutralMode.Brake)
            setInverted(InvertType.FollowMaster)
            configFactoryDefault()
        }

        rightSlave.apply {
            follow(rightMaster)
            setNeutralMode(NeutralMode.Brake)
            setInverted(InvertType.FollowMaster)
            configFactoryDefault()
        }
    }

    fun drive(drive: DriveSignal){
        leftMaster.set(drive.left)
        rightMaster.set(drive.right)
    }
}