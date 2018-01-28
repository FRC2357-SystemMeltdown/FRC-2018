package org.usfirst.frc.team2357.robot.subsystems.elevator.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommand;
import org.usfirst.frc.team2357.robot.OI;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem;

/**
 * The command to run the elevator manually. Hopefully we never need it.
 */
public class ManualElevatorCommand extends AbstractStateCommand {
	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	private final ElevatorSubsystem elevatorSubsystem;

	/**
	 * We would like to have {@link OI} final and initialized during construction
	 * but the commands are typically created as part of OI initialization so OI may
	 * not be available as this command is constructed. So, initialization of this
	 * field is put off until {@link #initialize()}.
	 */
	private /* final */ OI oi;

	public ManualElevatorCommand() {
		this.elevatorSubsystem = Robot.getInstance().getElevatorSubsystem();
		requires(this.elevatorSubsystem);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.oi = Robot.getInstance().getOI();
		if (!this.elevatorSubsystem.isManualOverride()) {
			this.elevatorSubsystem.setManualMode(true);
		}
	}

	@Override
	protected void execute() {
		super.execute();
		// TODO read from oi and send to elevator.
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		this.elevatorSubsystem.stop();
		super.end();
	}
}
