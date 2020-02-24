package frc.robot.subsystem

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.DigitalInput
import frc.robot.Constants
import frc.robot.Robotmap
import frc.robot.engine.TalonWrapper

object Conveyor {
    private val conveyorTalon = TalonWrapper(Robotmap.conveyorTalon)
    private val conveyorLimSwitch = DigitalInput(Constants.conveyorLimSwitchID)

    private const val forwardTicks = Constants.conveyorForwardTicks
    private var setPoint = 0

    init {
        conveyorTalon.setMagEncoder()
        conveyorTalon.configFactoryDefault()
        conveyorTalon.setPID(0.01,0.0,0.0)
    }

    fun conveyorTargetUpdate(){
        setPoint += forwardTicks
    }

    fun conveyorTargetBackward(){
        setPoint -= forwardTicks
    }

    fun updateConveyor(){
        conveyorTalon.set(ControlMode.Position, setPoint.toDouble())
    }

    fun conveyorManual(a: Double){
        conveyorTalon.set(a)
    }

    fun getOutput(): Double{
        return conveyorTalon.motorOutputVoltage
    }

    fun getLimSwitch(): Boolean{
        return conveyorLimSwitch.get()
    }

    fun resetSetpoint(){
        setPoint = conveyorTalon.selectedSensorPosition
    }
}