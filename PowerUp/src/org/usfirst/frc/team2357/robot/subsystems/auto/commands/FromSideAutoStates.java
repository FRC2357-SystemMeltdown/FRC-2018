package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.State;
import org.usfirst.frc.team2357.command.StateCommand;

public enum FromSideAutoStates implements State {
	/**
	 * Moves from wall to a spot next to but not touching the switch while raising
	 * elevator.
	 */
	// MOVE_WALL_TO_SWITCH(new MoveNextToScorePlatform(140.0)),

	/**
	 * Moves from wall to a spot next to but not touching the scale while raising
	 * elevator.
	 */
	// MOVE_WALL_TO_SCALE(new MoveNextToScorePlatform(300.0)),

	/**
	 * Moves from a spot next to but not touching switch to scoring. Move inches are
	 * a maximum without hitting limit switch detecting platform.
	 */
	// MOVE_TO_SCORE_SWITCH(new MoveToScorePlatform(30.0)),

	/**
	 * Moves from a spot next to but not touching scale to scoring. Move inches are
	 * a maximum without hitting limit switch detecting platform.
	 */
	// MOVE_TO_SCORE_SCALE(new MoveToScorePlatform(12.0)),

	/** Places the power cube on the platform. */
	// SCORE(new Score()),

	/** Backs off and lowers elevator. */
	// BACKOFF_AND_LOWER(24.0)
	;

	private final SmartStateCommand commandToRun;

	private FromSideAutoStates(final SmartStateCommand commandToRun) {
		this.commandToRun = commandToRun;
	}

	/**
	 * This year, the command in consultation with the subsystem will decide the
	 * next state.
	 */
	@Override
	public State getNextState() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateCommand getCommandToRun() {
		return this.commandToRun;
	}
}
