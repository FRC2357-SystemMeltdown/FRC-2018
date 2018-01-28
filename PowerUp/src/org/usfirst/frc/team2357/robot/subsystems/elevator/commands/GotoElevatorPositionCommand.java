package org.usfirst.frc.team2357.robot.subsystems.elevator.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommand;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;

/**
 * The command to run the elevator to a floor with positional pid control.
 */
public class GotoElevatorPositionCommand extends AbstractStateCommand {
	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	private final ElevatorSubsystem elevatorSubsystem;

	private final Floors floor;

	/**
	 * @param floor
	 *            the destination floor for this elevator command.
	 */
	public GotoElevatorPositionCommand(Floors floor) {
		this.elevatorSubsystem = Robot.getInstance().getElevatorSubsystem();
		requires(this.elevatorSubsystem);
		this.floor = floor;
	}

	/**
	 * Since we are just setting a new target, everything interesting can happen
	 * here.
	 */
	@Override
	protected void initialize() {
		super.initialize();
		// If manual mode active, go back to positional pid.
		if (this.elevatorSubsystem.isManualOverride()) {
			this.elevatorSubsystem.setManualMode(false);
		}
		// Now set the floor as the new target.
		this.elevatorSubsystem.gotoFloor(this.floor);
	}

	/**
	 * This command has set the target and can end right away.
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}
}
