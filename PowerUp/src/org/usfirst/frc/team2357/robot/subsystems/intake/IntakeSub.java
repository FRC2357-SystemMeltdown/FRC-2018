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

	public IntakeSub() {

	}

	public void initDefaultCommand() {

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
}
