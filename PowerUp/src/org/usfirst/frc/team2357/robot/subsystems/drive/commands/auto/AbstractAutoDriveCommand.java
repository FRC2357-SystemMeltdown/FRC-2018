package org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.DriveMode;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.AbstractDriveCommand;

/**
 * Base class for all autonomous drive commands. This class handles setting the
 * drive mode back to what it was before it ran and handles the ordinal intake
 * directions.
 */
public abstract class AbstractAutoDriveCommand extends AbstractDriveCommand {
	private DriveMode priorDriveMode;
	private final FixedIntakeDirection intakeDirection;

	/**
	 * Drives until interrupted or drives for the specified seconds.
	 * 
	 * @param intakeDirection
	 *            the direction the intake must point while driving.
	 * @param timeoutSeconds
	 *            time in seconds for this command to drive (use
	 *            {@link #RUN_FOREVER_TIMEOUT} to drive forever).
	 */
	public AbstractAutoDriveCommand(FixedIntakeDirection intakeDirection, double timeoutSeconds) {
		super(timeoutSeconds);
		this.intakeDirection = intakeDirection;
	}

	/**
	 * Drives without any timeout.
	 * 
	 * @param intakeDirection
	 *            the direction the intake must point while driving.
	 */
	public AbstractAutoDriveCommand(FixedIntakeDirection intakeDirection) {
		this(intakeDirection, RUN_FOREVER_TIMEOUT);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void initialize() {
		super.initialize();
		this.priorDriveMode = this.driveSubsystem.getDriveMode();
		this.driveSubsystem.setDriveMode(DriveMode.FIELD_RELATIVE);
		this.driveSubsystem.setFixedIntakeDirection(this.intakeDirection);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void end() {
		super.end();
		this.driveSubsystem.setDriveMode(this.priorDriveMode);
	}
}
