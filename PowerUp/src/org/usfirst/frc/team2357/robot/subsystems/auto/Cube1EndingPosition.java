package org.usfirst.frc.team2357.robot.subsystems.auto;

/**
 * An enumeration used to prioritize auto targets in cases where our
 * switch/scale platforms assignments create ambiguity as to where to score.
 * 
 * <p>
 * Note that these only make sense when combined with
 * {@link StartPosition#FAR_LEFT} and {@link StartPosition#FAR_RIGHT}. The other
 * start positions should assume {@link Cube1EndingPosition#ALWAYS_SWITCH}.
 * </p>
 * 
 * TODO consider if these could be used any non-middle.
 */
public enum Cube1EndingPosition {
	/** Side of left switch. */
	SIDE_OF_LEFT_SWITCH,

	/** Front (closer to the drive station) of the left switch. */
	FRONT_OF_LEFT_SWITCH,
	
	/** Side of right switch. */
	SIDE_OF_RIGHT_SWITCH,

	/** Front (closer to the drive station) of the right switch. */
	FRONT_OF_RIGHT_SWITCH,

	/** Front (closer to the drive station) of the left scale. */
	LEFT_SCALE,

	/** Front (closer to the drive station) of the right scale. */
	RIGHT_SCALE;
}
