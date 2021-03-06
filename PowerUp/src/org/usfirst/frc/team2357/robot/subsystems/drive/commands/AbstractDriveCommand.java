package org.usfirst.frc.team2357.robot.subsystems.drive.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommand;
import org.usfirst.frc.team2357.robot.OI;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

/**
 * Base class for all drive subsystem commands (operator and auto). Handles
 * getting the {@link DriveSubsystem} and the {@link OI}. Also optionally
 * manages a timeout.
 */
public abstract class AbstractDriveCommand extends AbstractStateCommand {
	/**
	 * The command timeout value for not timing out.
	 */
	public static final double RUN_FOREVER_TIMEOUT = -1.0;

	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	protected final DriveSubsystem driveSubsystem;

	/**
	 * We would like to have {@link OI} final and initialized during construction
	 * but the commands are typically created as part of OI initialization so OI may
	 * not be available as this command is constructed. So, initialization of this
	 * field is put off until {@link #initialize()}.
	 */
	protected /* final */ OI oi;

	private double timeoutSeconds = RUN_FOREVER_TIMEOUT;

	/**
	 * Drives until interrupted or drives for the specified seconds.
	 * 
	 * @param timeoutSeconds
	 *            time in seconds for this command to drive (use
	 *            {@link #RUN_FOREVER_TIMEOUT} to drive forever).
	 */
	public AbstractDriveCommand(double timeoutSeconds) {
		this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
		requires(this.driveSubsystem);
		this.timeoutSeconds = timeoutSeconds;
	}

	/**
	 * Drives without any timeout.
	 */
	public AbstractDriveCommand() {
		this(RUN_FOREVER_TIMEOUT);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Sets the timeout for stopping the robot if one was specified.
	 * </p>
	 */
	protected void initialize() {
		super.initialize();
		this.oi = Robot.getInstance().getOI();
		if (timeoutSeconds != -1.0) {
			setTimeout(timeoutSeconds);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if timed out and false otherwise.
	 */
	protected boolean isFinished() {
		if (timeoutSeconds != RUN_FOREVER_TIMEOUT) {
			return isTimedOut();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Stops the robot.
	 * </p>
	 */
	protected void end() {
		this.driveSubsystem.stop();
		super.end();
	}
}
