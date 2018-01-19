package org.usfirst.frc.team2357.command;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A {@link StateMachineCommand} is and alternative to {@link CommandGroup} that
 * implements a state machine (see the {@link State} interface). Each state has
 * a {@link StateCommand} to run when it is the current state and from each
 * state, the next state can be determined when current state's command is
 * finished ( {@link #isFinished()} returns true). The
 * {@link StateMachineCommand} ends when null is chosen for the next state.
 * 
 * <p>
 * Note that if the current state's {@link StateCommand} implements the
 * {@link NextStateProvider} interface, it will be asked to provide the next
 * state; otherwise, the current {@link State} itself will provide the next
 * state.
 * </p>
 * 
 * <p>
 * Unlike a {@link CommandGroup}, a {@link StateMachineCommand} does not carry
 * all the requirements of its contained commands. Only the requirements of the
 * command for the current state are in effect at any one time. The
 * {@link StateMachineCommand} itself must never require any {@link Subsystem}
 * instances.
 * </p>
 * 
 * <p>
 * If you want to set a {@link StateMachineCommand} as the default command for a
 * {@link Subsystem}, see the {@link DefaultStateCommandWrapper} class. You MUST
 * wrap the subsystem default {@link StateCommand} in an instance of
 * {@link DefaultStateCommandWrapper} and set the wrapper as the default
 * command.
 * </p>
 * 
 * <p>
 * While this class could allow any set of {@link State} implementations to
 * define the state machine, it was decided to enforce that it be an enumeration
 * that implements {@link State} so as to ensure that there is one place
 * available to find and understand the states and the state machine involved.
 * </p>
 * 
 * @author Steven Wasleski
 * 
 * @param <E>
 *            the {@link Enum} that implements {@link State} that is to be used
 *            for this {@link StateMachineCommand}.
 */
public abstract class StateMachineCommand<E extends Enum<E>> extends AbstractStateCommand {
	private State currentState;
	private final Class<E> stateEnumClazz;

	/**
	 * Creates a new state command. The name of this command will be set to its
	 * class name.
	 * 
	 * @param stateEnumClazz
	 *            the {@link Enum} that implements {@link State} that represents
	 *            this {@link StateMachineCommand}'s state machine.
	 * 
	 * @throws IllegalArgumentException
	 *             if the stateEnumClazz does not implement {@link State}.
	 */
	public StateMachineCommand(Class<E> stateEnumClazz) {
		super();
		if (!State.class.isAssignableFrom(stateEnumClazz)) {
			throw new IllegalArgumentException("The state enum class must implement the State interface.");
		}
		this.stateEnumClazz = stateEnumClazz;
	}

	/**
	 * Called by {@link #initialize()} to get the fixed or calculated initial
	 * {@link State}.
	 * 
	 * @return the {@link State} that will be set to current in
	 *         {@link #initialize()} (cannot be null).
	 */
	protected abstract State getInitialState();

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation starts the {@link StateCommand} for the initial
	 * state.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if {@link #getInitialState()} returns null.
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.currentState = getInitialState();
		if (this.currentState == null) {
			throw new IllegalStateException("The initial state cannot be null.");
		}
		startStateCommand();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * A {@link StateMachineCommand} is finished when the selection of a next
	 * state results in null for the new current state or the current
	 * {@link StateCommand} is interrupted.
	 * </p>
	 */
	@Override
	protected boolean isFinished() {
		return this.currentState == null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * When the {@link StateMachineCommand} is itself ends, make sure any
	 * running {@link StateCommand} is done and that there will be no
	 * next/current state.
	 * </p>
	 */
	@Override
	protected void end() {
		if (this.currentState != null) {
			this.currentState.getCommandToRun().cancel();
		}
		this.currentState = null;
		super.end();
	}

	/**
	 * Used to start the current {@link StateCommand}. The command is taken from
	 * {@link #currentState}.
	 * 
	 * <p>
	 * All {@link StateCommand} starts must come through this method.
	 * </p>
	 * 
	 * <p>
	 * This method enforces that the {@link State} be a member of the
	 * enumeration that was provided at construction. An
	 * {@link IllegalStateException} exception is thrown if this is not the case
	 * and the current state field will be set to null so as to terminate the
	 * {@link StateMachineCommand}.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the current state is not a member of the enumeration
	 *             passed to the constructor.
	 */
	private void startStateCommand() {
		if (this.currentState != null) {
			if (this.currentState.getClass() != this.stateEnumClazz) {
				this.currentState = null;
				throw new IllegalStateException(
						"The state to run must be a member of the enumeration for the state command.");
			}
			StateCommand cmd = this.currentState.getCommandToRun();
			cmd.setStateMachine(this);
			cmd.start();
		}
	}

	/**
	 * Called when we need the next {@link State}. That is, when the current
	 * command's isFinished method returns true. Called from
	 * {@link StateCommand#end()}.
	 * 
	 * <p>
	 * Note that the {@link StateMachineCommand#currentState} field is set by
	 * this method.
	 * </p>
	 */
	void startNextState() {
		if (this.currentState != null) {
			this.currentState = this.currentState.chooseNextState();
			startStateCommand();
		}
	}

	/**
	 * If the current command is interrupted, the whole state machine ends.
	 * Called from {@link StateCommand#interrupted()}.
	 * 
	 * <p>
	 * Note that the {@link StateMachineCommand#currentState} field is set to
	 * null by this method.
	 * </p>
	 */
	void terminate() {
		this.currentState = null;
	}
}
