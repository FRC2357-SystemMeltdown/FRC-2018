package org.usfirst.frc.team2357.command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * If a {@link StateMachineCommand} is to be set as the default command for a
 * subsystem, it MUST be wrapped inside an instance of this wrapper first. This
 * is due to the fact that a subsystem mandates that its default command MUST
 * require the subsystem (there is no real point to this but it is the way it
 * is) and a {@link StateMachineCommand} itself never requires any subsystem. The
 * commands the run for the states do.
 * 
 * @author Steven Wasleski
 */
public class DefaultStateCommandWrapper extends Command {
	private final StateMachineCommand<?> wrappedStateCommand;
	private final Subsystem targetSubsystem;

	public DefaultStateCommandWrapper(StateMachineCommand<?> stateCommand, Subsystem target) {
		if (stateCommand == null) {
			throw new IllegalArgumentException("The state command to wrap cannot be null.");
		}
		if (target == null) {
			throw new IllegalArgumentException("The target subsystem of the state command cannot be null.");
		}
		this.wrappedStateCommand = stateCommand;
		this.targetSubsystem = target;
		requires(this.targetSubsystem);
	}

	@Override
	protected void execute() {
		super.execute();
		if (!this.wrappedStateCommand.isRunning()) {
			this.wrappedStateCommand.start();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
