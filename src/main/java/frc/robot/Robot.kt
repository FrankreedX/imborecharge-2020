package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.robot.commands.FireSequenceCommand
import frc.robot.engine.CheesyDrive
import frc.robot.subsystem.*

class Robot: TimedRobot() {
    var conveyorAuto = true
    var shooterAuto = true
    var shooting = false
    var climb = false

    private val intakeLimSwitch = Intake.getLimSwitch()
    private val conveyorLimSwitch = Conveyor.getLimSwitch()
    private val rollerLimSwitch = Roller.getLimSwitch()
    private var intakeLimPressed = false

    private val fireSequence = FireSequenceCommand()

    override fun autonomousInit() {
        fireSequence.start()
    }

    override fun autonomousPeriodic() {
        Scheduler.getInstance().run()
    }

    override fun teleopInit() {
        fireSequence.cancel()
    }

    override fun teleopPeriodic() {
        SmartDashboard.putBoolean("Shooting",shooting)
        SmartDashboard.putBoolean("Climbing", climb)
        SmartDashboard.putBoolean("Conveyor Auto", conveyorAuto)
        SmartDashboard.putBoolean("Shooter Auto", shooterAuto)

        SmartDashboard.putBoolean("Intake Lim", Intake.getLimSwitch())

        //if robot starts driving by controller, stop fire sequence
        if (OI.driving) {
            shooting = false
        } else if (OI.FireSequenceButton)
            //toggle button to start shooting
            shooting = true

        if (OI.shooterManual) {
            shooterAuto = false
            fireSequence.cancel()
        }

        if (OI.shooterAuto) {
            shooterAuto = true
        }

        climb = OI.flipflap

        //drivetrain
        Drivetrain.drive(
                CheesyDrive.updateCheesy(
                        if (OI.quickTurn) OI.leftTrigger-OI.rightTrigger else OI.turn,
                        OI.throttle,
                        OI.quickTurn
                )
        )
        if (!climb) {
            if (!shooting) {
                println("Driving")
                fireSequence.cancel()
                //intake
                Intake.intakeRun(OI.bigStick)

                //conveyor
                //another possible solution: place lim switch so that when ball passes through
                if (OI.hat != 0.0) conveyorAuto = false
                if (OI.conveyorAuto) conveyorAuto = true
                if (conveyorAuto && (!rollerLimSwitch || !conveyorLimSwitch)) {
                    intakeLimPressed = if (intakeLimSwitch) {
                        Conveyor.conveyorTargetUpdate()
                        true
                    } else false
                    if (OI.conveyorForward) Conveyor.conveyorTargetUpdate() //have not been tested
                    if (OI.conveyorForward) Conveyor.conveyorTargetBackward() //have not been tested
                    Conveyor.updateConveyor()
                } else Conveyor.conveyorManual(OI.hat)

                //roller will not run if it's forced to run forward when limit switch is pressed, otherwise follow conveyor at all times. No Manual
                if (!rollerLimSwitch || Conveyor.getOutput() < 0) Roller.rollerFollow() else Roller.stop()

            } else {
                println("Shooting")
                //fire sequence automation
                if (shooterAuto) fireSequence.start()
                else {
                    //fire sequence manual
                    //shooter may have Jacob's system of dividing the input range into different set speeds instead of continuous range
                    Shooter.runShooter(OI.bigStick)
                    Conveyor.conveyorManual(OI.hat)
                    Roller.rollerFollow()
                    Intake.follow()
                }
            }
        } else {
            //climb
            Climb.lower(OI.bigStick)
        }


        Scheduler.getInstance().run()
    }

    override fun disabledPeriodic() {

    }
}