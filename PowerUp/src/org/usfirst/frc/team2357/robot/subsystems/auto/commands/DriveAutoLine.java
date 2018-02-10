package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;

/**
 * A simple autonomous command that just drives over the AUTO LINE for 5 points.
 */
public class DriveAutoLine extends AbstractStateCommandGroup {
	public DriveAutoLine() {
		addSequential(new AutoDriveSegment(null, 150.0, 0.0, 0.7));
	}
}
