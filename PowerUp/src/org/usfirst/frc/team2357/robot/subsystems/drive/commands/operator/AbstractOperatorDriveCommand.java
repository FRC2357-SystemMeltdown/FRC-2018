package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.AbstractDriveCommand;

/**
 * Abstract base class for operator drive commands. It uses mecanum drive to
 * move the robot with Cartesian y and x. The rotation value is dependent on
 * current {@link DriveSubsystem} settings and user input.
 */
public abstract class AbstractOperatorDriveCommand extends AbstractDriveCommand {
	/**
	 * Subclasses must implement to return the next Cartesian Y value.
	 * 
	 * @return the next Cartesian Y value.
	 */
	protected abstract double getNextY();

	/**
	 * Subclasses must implement to return the next Cartesian X value.
	 * 
	 * @return the next Cartesian X value.
	 */
	protected abstract double getNextX();

	/**
	 * Subclasses want to read sticks to support manual rotation must override this.
	 * 
	 * @return the next manual stick derived rotation value.
	 */
	protected double getNextStickManualRotation() {
		return 0.0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Drives the robot with the subclass supplied Cartesian x and y values.
	 * </p>
	 */
	protected void execute() {
		super.execute();
		this.driveSubsystem.cartesianDrive(getNextY(), getNextX(), getNextStickManualRotation());
	}
}
