package frc.robot

object Constants {
    const val neoMaxRPM = 5700

    const val shooterKP = 6e-5
    const val shooterKI = 0.0
    const val shooterKD = 0.0
    const val shooterKFF = 0.000015
    const val shooterKMaxOutput = 1.0
    const val shooterKMinOutput = -1.0

    const val ballSystemOverallSpeed = 0.5

    const val conveyorForwardTicks = 100

    const val shooterIdleSpeed = 1000.0 //shooter speed when vision target not visible, this is like spinning up your machine gun in tf2 without firing

    const val drivetrainAlignmentPTerm = 0.001
    const val drivetrainAlignmentTolerance = 1

    const val shooterSpeedPTerm = 0.001
    const val shooterSpeedTolerance = 100.0

    const val climbPDPSlot = 3

    const val climbCurrentDifferrence = 10

    const val intakeLimSwitchID = 1
    const val conveyorLimSwitchID = 2
    const val rollerLimSwitchID = 3
}