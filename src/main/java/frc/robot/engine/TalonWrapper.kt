package frc.robot.engine

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonSRX

class TalonWrapper(
        deviceID: Int
) : TalonSRX(deviceID) {

    var lastSpeed = Double.NaN
    var lastControlMode = controlMode ?: ControlMode.PercentOutput

    @Synchronized
    fun set(value: Double) {
        if (lastSpeed != value) {
            lastSpeed = value
            super.set(lastControlMode, value)
        }
    }

    @Synchronized
    fun changeControlMode(mode: ControlMode) {
        if (mode != lastControlMode) {
            lastControlMode = mode
            super.set(mode, lastSpeed)
        }
    }


    @Synchronized
    fun setMagEncoder() {
        configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10)
    }

    @Synchronized
    fun setSlave(master: TalonSRX) = apply {
        changeControlMode(ControlMode.Follower)
        set(master.deviceID.toDouble())
    }

    @Synchronized
    infix fun slaveTo(master: TalonSRX) = setSlave(master)

    @Synchronized
    fun setPID(Kp: Double, Ki: Double, Kd: Double, Kf: Double = 0.0, slot: Int = 0, iZone: Int = 0) {
        config_IntegralZone(slot, iZone, 0)
        config_kP(slot, Kp, 0)
        config_kI(slot, Ki, 0)
        config_kD(slot, Kd, 0)
        config_kF(slot, Kf, 0)
    }
}