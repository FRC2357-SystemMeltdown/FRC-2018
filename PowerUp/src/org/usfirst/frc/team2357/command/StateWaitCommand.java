package org.usfirst.frc.team2357.command;

public class StateWaitCommand extends AbstractStateCommand {
	public StateWaitCommand(double timeout) {
		super();
		super.setTimeout(timeout);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
}
