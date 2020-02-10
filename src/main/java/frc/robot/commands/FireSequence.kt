package frc.robot.commands

import edu.wpi.first.wpilibj.command.Command
import frc.robot.Constants
import frc.robot.engine.DriveSignal
import frc.robot.subsystem.Conveyor
import frc.robot.subsystem.Drivetrain
import frc.robot.subsystem.Roller
import frc.robot.subsystem.Shooter
import frc.robot.subsystem.Vision.visionTargetAngleDisplacement
import frc.robot.subsystem.Vision.visionTargetDistance
import frc.robot.subsystem.Vision.visionTargetVisible
import kotlin.math.abs

class FireSequence: Command() {
    private var drivetrainReady = false
    private var shooterReady = false

    override fun execute() {
        val shooterSpeed = visionTargetDistance * Constants.shooterSpeedPTerm
        if (!visionTargetVisible){
            Shooter.runShooter(Constants.shooterIdleSpeed)
        } else {
            Shooter.runShooter(visionTargetDistance)
            if (Shooter.getSpeed()> shooterSpeed - Constants.shooterSpeedTolerance && Shooter.getSpeed() < shooterSpeed + Constants.shooterSpeedTolerance) shooterReady = true
        }

        val turnValue = visionTargetAngleDisplacement * Constants.drivetrainAlignmentPTerm
        Drivetrain.drive(DriveSignal(turnValue, -turnValue))
        if (abs(visionTargetAngleDisplacement) < Constants.drivetrainAlignmentTolerance) drivetrainReady = true

        if (shooterReady && drivetrainReady || !Roller.getLimSwitch()){
            Conveyor.conveyorManual(Constants.ballSystemOverallSpeed)
        }
    }

    override fun end() {
        drivetrainReady = false
        shooterReady = false
    }

    override fun isFinished(): Boolean {
        return false
    }
}