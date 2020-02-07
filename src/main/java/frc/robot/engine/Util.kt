package frc.robot.engine

/** Stores left + right motor speed + brake value
 * @param left left power/speed
 * @param right right power/speed
 * @param brake brake mode or not
*/
data class DriveSignal(val left: Double = 0.0, val right: Double = 0.0, val brake: Boolean = false) {
    operator fun times(mul: Int): DriveSignal {
        return DriveSignal(this.left * mul, this.right * mul, this.brake)
    }
}

