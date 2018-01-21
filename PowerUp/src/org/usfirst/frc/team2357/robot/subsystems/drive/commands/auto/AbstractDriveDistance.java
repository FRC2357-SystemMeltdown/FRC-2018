package org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.AbstractDriveCommand;

/**
 * Moves a distance while maintaining orientation of the intake. If the intake
 * is already at the orientation, the distance will be accurate. If it is not,
 * the distance will come up short.
 * 
 * TODO see if we can adjust the distance based off the current angle and get it
 * accurate in all cases.
 */
public abstract class AbstractDriveDistance extends AbstractAutoDriveCommand {
	private final double inches;
	private int targetClicks;

	/**
	 * Drives forever until the specified inches are reached.
	 * 
	 * @param intakeDirection
	 *            the direction the intake must point while driving.
	 * @param inches
	 *            the distance to drive.
	 */
	public AbstractDriveDistance(FixedIntakeDirection intakeDirection, double inches) {
		this(intakeDirection, inches, AbstractDriveCommand.RUN_FOREVER_TIMEOUT);
	}

	/**
	 * Drives for the specified inches or for the specified seconds, whichever comes
	 * first.
	 * 
	 * @param intakeDirection
	 *            the direction the intake must point while driving.
	 * @param inches
	 *            the distance to drive.
	 * @param timeoutSeconds
	 *            time in seconds for this command to drive (use
	 *            {@link #RUN_FOREVER_TIMEOUT} to drive forever).
	 */
	public AbstractDriveDistance(FixedIntakeDirection intakeDirection, double inches, double timeoutSeconds) {
		super(intakeDirection, timeoutSeconds);
		this.inches = inches;
	}

	/**
	 * Starts up distance driving on the drive subsystem.
	 */
	protected void initialize() {
		super.initialize();
		this.targetClicks = this.driveSubsystem.startMoveInches(this.inches);
	}

	@Override
	protected void execute() {
		super.execute();
		// TODO add code to drive without rotation on proper axis.
	}

	/**
	 * Returns true if the inches have been moved or the timeout is hit.
	 */
	protected boolean isFinished() {
		return this.driveSubsystem.isPositionOnTarget(this.targetClicks) || super.isFinished();
	}

	/**
	 * Stops moving inches on the drive subsystem.
	 */
	protected void end() {
		this.driveSubsystem.stopMoveInches();
		super.end();
	}
}
