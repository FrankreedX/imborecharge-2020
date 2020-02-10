package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import frc.robot.commands.FireSequence
import frc.robot.engine.CheesyDrive
import frc.robot.subsystem.*

class Robot: TimedRobot() {
    var visionTargetVisible = false
    var visionTargetDistance = 0.0
    var visionTargetAngleDisplacement = 0.0
    var conveyorAuto = true
    var shooterEnabled = false
    var shooterAuto = true

    private val intakeLimSwitch = Intake.getLimSwitch()
    private val conveyorLimSwitch = Conveyor.getLimSwitch()
    private val rollerLimSwitch = Roller.getLimSwitch()
    private var intakeLimPressed = false

    override fun teleopInit() {
    }

    override fun teleopPeriodic() {
        //drivetrain
        Drivetrain.drive(
                CheesyDrive.updateCheesy(
                        if (OI.quickTurn) OI.leftTrigger-OI.rightTrigger else OI.turn,
                        OI.throttle,
                        OI.quickTurn
                )
        )

        //intake
        if (!shooterEnabled) Intake.intakeRun(OI.bigStick)

        //conveyor
        if (OI.hat != 0.0) conveyorAuto = false
        if (OI.conveyorAuto) conveyorAuto = true
        if (!rollerLimSwitch || !conveyorLimSwitch)
            if (conveyorAuto){
                intakeLimPressed = if (intakeLimSwitch) {
                    Conveyor.conveyorTargetUpdate()
                    true
                } else false
                Conveyor.updateConveyor()
            } else Conveyor.conveyorManual(OI.hat)
        else Conveyor.conveyorManual(OI.hat)

        //roller
        if (!rollerLimSwitch||Conveyor.getOutput()<0) Roller.rollerFollow() else Roller.stop()

        //fire sequence automation
        OI.FireSequenceButton.toggleWhenPressed(FireSequence())

        //climb
    }

    override fun disabledPeriodic() {

    }
}