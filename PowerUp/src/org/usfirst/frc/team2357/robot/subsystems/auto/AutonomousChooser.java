package org.usfirst.frc.team2357.robot.subsystems.auto;

/**
 * One instance of this interface will be available per match based off
 * configuration settings. The autonomous command to run can be accessed
 * directly from this single instance.
 */
interface AutonomousChooser {
	/**
	 * Returns the {@link AutonomousMode} to run this match. If any error occurs
	 * while selecting the {@link AutonomousMode}, the default will be returned.
	 * 
	 * @return this match's {@link AutonomousMode}. Implementations must take
	 *         care to never return null (see
	 *         {@link AutonomousMode#getDefault()}).
	 */
	public AutonomousMode getAutonomousMode();
}