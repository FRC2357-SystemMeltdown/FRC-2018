package org.usfirst.frc.team2357.robot.subsystems.auto;

/**
 * An enumeration used to prioritize auto targets in cases where our
 * switch/scale platforms assignments create ambiguity as to where to score.
 * 
 * <p>
 * Note that these only make sense when combined with
 * {@link StartPosition#FAR_LEFT} and {@link StartPosition#FAR_RIGHT}. The other
 * start positions should assume {@link TargetPreference#ALWAYS_SWITCH}.
 * </p>
 */
public enum TargetPreference {
	/** Always switch. */
	ALWAYS_SWITCH,

	/** Always scale. */
	ALWAYS_SCALE,

	/** Prefer switch if switch and scale on same side. */
	PREFER_SWITCH,

	/** Prefer scale if switch and scale on same side. */
	PREFER_SCALE;
}
