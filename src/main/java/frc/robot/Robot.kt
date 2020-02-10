package frc.robot

import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.TimedRobot
import frc.robot.engine.CheesyDrive
import frc.robot.subsystem.*

class Robot: TimedRobot() {
    var vision = 0.0 //TODO: Vision goes here
    var conveyorAuto = true
    var shooterEnabled = false
    var shooterAuto = true

    private val intakeLimSwitch = DigitalInput(1).get()
    private val conveyorLimSwitch = DigitalInput(2).get()
    private val rollerLimSwitch = DigitalInput(3).get()
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
        if (OI.hat == -1.0) conveyorAuto = false
        if (OI.conveyorAuto) conveyorAuto = true
        if (!rollerLimSwitch && !conveyorLimSwitch)
            if (conveyorAuto) {
                intakeLimPressed = if (intakeLimSwitch) {
                    Conveyor.conveyorTargetUpdate()
                    true
                } else false
                Conveyor.updateConveyor()
            } else Conveyor.conveyorManual(OI.hat)
        else if (OI.hat == 180.0) Conveyor.conveyorManual(OI.hat) else Conveyor.stop()

        //roller
        if (!rollerLimSwitch||Conveyor.getOutput()<0) Roller.rollerFollow() else Roller.stop()

        //fire sequence automation
        if (OI.startFireSequence)

        //climb
    }

    override fun disabledPeriodic() {

    }
}