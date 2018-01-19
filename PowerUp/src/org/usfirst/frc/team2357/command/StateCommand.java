package org.usfirst.frc.team2357.command;

import edu.wpi.first.wpilibj.command.Command;

/**
 * An interface for all {@link Command}s that will be used as the command for a
 * {@link State} as part of a {@link StateMachineCommand}.
 * 
 * @author Steven Wasleski
 */
public interface StateCommand {
	/**
	 * Used by the containing {@link StateMachineCommand} to set itself for
	 * callbacks.
	 * 
	 * @param stateMachine
	 *            the containing {@link StateMachineCommand}.
	 */
	public void setStateMachine(StateMachineCommand<?> stateMachine);

	/**
	 * Shadow of {@link Command#cancel()}.
	 */
	public void cancel();

	/**
	 * Shadow of {@link Command#start()}.
	 */
	public void start();
}
