package org.usfirst.frc.team2357.command;

/**
 * This interface is extended by {@link State} (which, in turn is implemented by
 * many enums). This interface is also implemented by certain
 * {@link StateCommand}s. The {@link StateCommand} implements this interface if
 * it wants to determine the next {@link State}. If the current state's command
 * does not implement this interface, the current state's implementation of this
 * interface will be asked for the next state (see
 * {@link State#chooseNextState()}).
 * 
 * @author Steven Wasleski
 */
public interface NextStateProvider {
	/**
	 * When the {@link StateCommand} for the current {@link State} finishes the
	 * next state will be determined from the current command (if it implements
	 * this interface) or the current state via this method.
	 * 
	 * @return the next state of the {@link StateMachineCommand} state machine.
	 *         Return null to terminate the {@link StateMachineCommand}.
	 */
	public State getNextState();
}
