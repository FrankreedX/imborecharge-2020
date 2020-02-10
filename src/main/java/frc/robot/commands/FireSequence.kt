package frc.robot.commands

import frc.robot.Constants
import frc.robot.engine.DriveSignal
import frc.robot.subsystem.Drivetrain
import frc.robot.subsystem.Shooter

object FireSequence {
    var visionTargetVisible = false
    var visionTargetDistance = 0.0
    var visionTargetAngleDisplacement = 5.0

    fun shooterSpinUp(shooterTarget: Double){
        if (!visionTargetVisible){
            Shooter.runShooter(Constants.shooterIdleSpeed)
        } else {
            Shooter.runShooter(shooterTarget)
        }
    }

    fun robotAlign(){
        val turnValue = visionTargetAngleDisplacement*Constants.drivetrainAlignmentPTerm
        Drivetrain.drive(DriveSignal(turnValue, -turnValue))
    }

    fun unloadBalls(shooterTarget: Double){
        if (Shooter.getSpeed() == )
    }
}