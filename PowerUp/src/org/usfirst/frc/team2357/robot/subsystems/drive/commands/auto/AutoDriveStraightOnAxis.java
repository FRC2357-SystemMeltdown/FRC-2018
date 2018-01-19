package org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

/**
 * Used to drive the robot straight in a Cartesian x or y direction. The line
 * may be field or robot relative depending on the current
 * {@link DriveSubsystem#isFieldRelative()} setting.
 */
public class AutoDriveStraightOnAxis extends AbstractAutoDriveCommand {
	private double move = 0.0;
	private boolean y = true;

	/**
	 * Drives until interrupted or drives for the specified milliseconds.
	 * 
	 * @param moveValue
	 *            the arcade drive style move value.
	 * @param y
	 *            true for a y axis move and false for x.
	 * @param msTime
	 *            time in milliseconds for this command to drive (use -1.0 to drive
	 *            forever).
	 */
	public AutoDriveStraightOnAxis(double moveValue, boolean y, double msTime) {
		super(msTime);
		this.move = moveValue;
		this.y = y;
	}

	@Override
	protected double getNextY() {
		return this.y ? this.move : 0.0;
	}

	@Override
	protected double getNextX() {
		return this.y ? 0.0 : this.move;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void end() {
		super.end();
	}
}
