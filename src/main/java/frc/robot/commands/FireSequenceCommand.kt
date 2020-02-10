package frc.robot.commands

import edu.wpi.first.wpilibj.command.Command
import frc.robot.Constants
import frc.robot.engine.DriveSignal
import frc.robot.subsystem.*
import kotlin.math.abs

class FireSequenceCommand: Command() {
    private var drivetrainReady = false
    private var shooterReady = false

    override fun execute() {
        val shooterSpeed = Vision.visionTargetDistance * Constants.shooterSpeedPTerm
            Shooter.runShooter(Vision.visionTargetDistance)
            shooterReady = Shooter.getSpeed()> shooterSpeed - Constants.shooterSpeedTolerance && Shooter.getSpeed() < shooterSpeed + Constants.shooterSpeedTolerance

        val turnValue = Vision.visionTargetAngleDisplacement * Constants.drivetrainAlignmentPTerm
        Drivetrain.drive(DriveSignal(turnValue, -turnValue))
        drivetrainReady = abs(Vision.visionTargetAngleDisplacement) < Constants.drivetrainAlignmentTolerance

        if (shooterReady && drivetrainReady || !Roller.getLimSwitch()){
            Conveyor.conveyorManual(Constants.ballSystemOverallSpeed)
            Intake.intakeRun(Constants.ballSystemOverallSpeed)
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