package org.usfirst.frc.team2357.robot.subsystems.auto;

/**
 * A left/right enumeration used to indicate on which side of the field our
 * switch and scale platforms are from the point of view of the drive team.
 */
public enum PlatformSide {
	/** The scale or switch platform for our cubes is on the right. */
	RIGHT,
	/** The scale or switch platform for our cubes is on the left. */
	LEFT,
	/** The value before the data becomes available. */
	UNKNOWN;
}
