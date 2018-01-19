package org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.DriveMode;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.AbstractDriveCommand;

/**
 * Base class for all autonomous drive commands.
 */
public abstract class AbstractAutoDriveCommand extends AbstractDriveCommand {
	private DriveMode priorDriveMode = DriveMode.AUTO;

	/**
	 * Drives until interrupted or drives for the specified seconds.
	 * 
	 * @param timeoutSeconds
	 *            time in seconds for this command to drive (use
	 *            {@link #RUN_FOREVER_TIMEOUT} to drive forever).
	 */
	public AbstractAutoDriveCommand(double timeoutSeconds) {
		super(timeoutSeconds);
	}

	/**
	 * Drives without any timeout.
	 */
	public AbstractAutoDriveCommand() {
		this(RUN_FOREVER_TIMEOUT);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void initialize() {
		super.initialize();
		this.priorDriveMode = this.driveSubsystem.getDriveMode();
		this.driveSubsystem.setDriveMode(DriveMode.AUTO);
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
