package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.AbstractDriveCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.AbstractAutoDriveCommand;

/**
 * Use this command to drive a segment of an autonomous path. The x and y inputs
 * are field relative and will be scaled downward at the start and end of the
 * drive segment.
 */
public class AutoDriveSegment extends AbstractAutoDriveCommand {
	private static final double STARTUP_SPEED_SCALING = 0.6;
	private static final double ENDING_SPEED_SCALING = 0.6;
	private final double inches;
	private final double maxX;
	private final double maxY;
	private int targetClicks;

	/**
	 * Drives forever until the specified inches are reached.
	 * 
	 * @param intakeDirection
	 *            the direction the intake must point while driving.
	 * @param inches
	 *            the distance to drive.
	 * @param maxX
	 *            field relative maximum X (left and right) speed.
	 * @param maxY
	 *            field relative maximum Y (up and down field) speed.
	 */
	public AutoDriveSegment(FixedIntakeDirection intakeDirection, double inches, double maxX, double maxY) {
		this(intakeDirection, inches, maxX, maxY, AbstractDriveCommand.RUN_FOREVER_TIMEOUT);
	}

	/**
	 * Drives for the specified inches or for the specified seconds, whichever comes
	 * first.
	 * 
	 * @param intakeDirection
	 *            the direction the intake must point while driving.
	 * @param inches
	 *            the distance to drive.
	 * @param maxX
	 *            field relative maximum X (left and right) speed.
	 * @param maxY
	 *            field relative maximum Y (up and down field) speed.
	 * @param timeoutSeconds
	 *            time in seconds for this command to drive (use
	 *            {@link #RUN_FOREVER_TIMEOUT} to drive forever).
	 */
	public AutoDriveSegment(FixedIntakeDirection intakeDirection, double inches, double maxX, double maxY,
			double timeoutSeconds) {
		super(intakeDirection, timeoutSeconds);
		this.inches = inches;
		this.maxX = maxX;
		this.maxY = maxY;
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
		double ySpeed = maxY;
		double xSpeed = maxX;
		double targetObtainedPercentage = this.driveSubsystem.getTargetObtainedPercentage(this.targetClicks);
		if (targetObtainedPercentage < .2) {
			ySpeed *= STARTUP_SPEED_SCALING;
			xSpeed *= STARTUP_SPEED_SCALING;
		} else if (targetObtainedPercentage > .8) {
			ySpeed *= ENDING_SPEED_SCALING;
			xSpeed *= ENDING_SPEED_SCALING;
		}
		this.driveSubsystem.cartesianDrive(ySpeed, xSpeed, 0.0);
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
