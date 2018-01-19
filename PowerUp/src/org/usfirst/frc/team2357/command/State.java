package org.usfirst.frc.team2357.command;

/**
 * An instance of this interface represents one of several states that a
 * {@link StateMachineCommand} will pass through to execute its goal. Each
 * {@link StateMachineCommand} MUST use an enum that implements this interface
 * to define the command's state machine. Each {@link State} / enum value must
 * be given its command to run on construction.
 * 
 * @author Steven Wasleski
 */
public interface State extends NextStateProvider {
	/**
	 * Called upon entering the {@link State} to get the {@link StateCommand} to
	 * execute while in this state.
	 * 
	 * @return the {@link StateCommand} to execute in this {@link State}.
	 */
	public StateCommand getCommandToRun();

	/**
	 * This method should not be overridden. If the command is a
	 * {@link NextStateProvider}, it will be asked for the next state. If the
	 * command is not a {@link NextStateProvider}, this {@link State} will
	 * itself determine the next state.
	 * 
	 * @return the next {@link State}. If null, the {@link StateMachineCommand}
	 *         will terminate.
	 */
	public default /* final */ State chooseNextState() {
		if (getCommandToRun() instanceof NextStateProvider) {
			return ((NextStateProvider) getCommandToRun()).getNextState();
		}
		return getNextState();
	}
}
