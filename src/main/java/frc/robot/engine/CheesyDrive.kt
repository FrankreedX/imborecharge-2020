package frc.robot.engine

import kotlin.math.abs

object CheesyDrive {
    private var m_turnInput: Double = 0.0
    private var m_speedInput: Double = 0.0

    private var k_curveTurnSens: Double = 1.0
    private var k_throttleQuickTurnThreshold: Double = 0.2
    private var k_quickStopAccumulationFactor: Double = 0.2

    private var m_motorSpeeds: DriveSignal = DriveSignal(0.0, 0.0, false)

    private var m_quickTurn: Boolean = false
    private var m_quickStopAccum: Double = 0.0

    private var m_brake: Boolean = false

    private fun updateInputs(turn: Double, speed: Double) {
        m_turnInput = turn
        m_speedInput = speed
    }

    private fun updateQuickTurn(qt: Boolean) {
        m_quickTurn = qt
    }

    private fun update() {
        val throttle: Double = m_speedInput
        val turn: Double = m_turnInput

        val powerOverride: Double
        val turnPower: Double

        if (m_quickTurn) {
            if (abs(throttle) < k_throttleQuickTurnThreshold) {
                m_quickStopAccum = (1 - k_quickStopAccumulationFactor) * m_quickStopAccum + k_quickStopAccumulationFactor * 2 * turn
            }
            powerOverride = 1.0
            turnPower = turn
        } else {
            powerOverride = 0.0
            turnPower = abs(throttle) * turn * k_curveTurnSens - m_quickStopAccum
        }

        m_quickStopAccum = if (m_quickStopAccum > 1) m_quickStopAccum - 1 else if (m_quickStopAccum < -1) m_quickStopAccum + 1 else 0.0

        var rightMotorPower = throttle - turnPower
        var leftMotorPower = throttle + turnPower


        when {
            leftMotorPower > 1.0 -> {
                rightMotorPower -= powerOverride * (leftMotorPower - 1.0)
                leftMotorPower = 1.0
            }
            rightMotorPower > 1.0 -> {
                leftMotorPower -= powerOverride * (rightMotorPower - 1.0)
                rightMotorPower = 1.0
            }
            leftMotorPower < -1.0 -> {
                rightMotorPower += powerOverride * (-1.0 - leftMotorPower)
                leftMotorPower = -1.0
            }
            rightMotorPower < -1.0 -> {
                leftMotorPower += powerOverride * (-1.0 - rightMotorPower)
                rightMotorPower = -1.0
            }
        }

        m_motorSpeeds = DriveSignal(leftMotorPower, rightMotorPower, m_brake)
    }

    fun updateCheesy(turn: Double = 0.0, speed: Double = 0.0, quickTurn: Boolean = false): DriveSignal {
        updateInputs(turn, speed)
        updateQuickTurn(quickTurn)
        update()
        return m_motorSpeeds
    }
}
