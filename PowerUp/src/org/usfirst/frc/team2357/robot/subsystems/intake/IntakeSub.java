package org.usfirst.frc.team2357.robot.subsystems.intake;

import org.usfirst.frc.team2357.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSub extends Subsystem {

	private final WPI_TalonSRX intakeMotor1 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_1);
	private final WPI_TalonSRX intakeMotor2 = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_2);

	private final double INTAKE_IN_SPEED = 0.7;
	private final double INTAKE_OUT_SPEED = 0.7;

	public IntakeSub() {

	}

	public void initDefaultCommand() {

	}

	public void intakeIn() {
		intakeMotor1.set(ControlMode.PercentOutput, INTAKE_IN_SPEED);
		intakeMotor2.set(ControlMode.PercentOutput, -INTAKE_IN_SPEED);
	}

	public void intakeOut() {
		intakeMotor1.set(ControlMode.PercentOutput, -INTAKE_OUT_SPEED);
		intakeMotor2.set(ControlMode.PercentOutput, INTAKE_OUT_SPEED);
	}

	public void stop() {
		intakeMotor1.set(ControlMode.PercentOutput, 0.0);
		intakeMotor2.set(ControlMode.PercentOutput, 0.0);

	}
}
