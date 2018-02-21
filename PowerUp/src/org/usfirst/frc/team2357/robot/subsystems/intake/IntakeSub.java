package org.usfirst.frc.team2357.robot.subsystems.intake;

import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeStateCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSub extends Subsystem {

	private final WPI_TalonSRX intakeMotor1 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_1);
	private final WPI_TalonSRX intakeMotor2 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_2);
	
	private final VictorSP dropMotor = new VictorSP(RobotMap.INTAKE_DROP_MOTOR);
	
	private IntakeState state = IntakeState.RAISED;
	
	public enum IntakeState {
		RAISED,
		
		LOWERED,
		
		RAISING,
		
		LOWERING;
	}

	public IntakeSub() {

	}

	public void initDefaultCommand() {
		new IntakeStateCommand();
	}

	public void intakeIn(double speed) {
		intakeMotor1.set(ControlMode.PercentOutput, speed);
		intakeMotor2.set(ControlMode.PercentOutput, -speed);
	}

	public void intakeOut(double speed) {
		intakeMotor1.set(ControlMode.PercentOutput, -speed);
		intakeMotor2.set(ControlMode.PercentOutput, speed);
	}

	public void stop() {
		intakeMotor1.set(ControlMode.PercentOutput, 0.0);
		intakeMotor2.set(ControlMode.PercentOutput, 0.0);

	}
	
	public void raiseIntake() {
		dropMotor.set(0.5);
	}
	
	public void lowerIntake(){
		dropMotor.set(-0.5);
	}

	public IntakeState getState() {
		return state;
	}

	public void setState(IntakeState state) {
		this.state = state;
	}
	
}
