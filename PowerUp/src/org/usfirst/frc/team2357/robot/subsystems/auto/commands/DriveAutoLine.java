package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.AbstractDriveDistance;

/**
 * A simple autonomous command that just drives over the AUTO LINE for 5 points.
 */
public class DriveAutoLine extends AbstractDriveDistance {
	public DriveAutoLine() {
		super(null, 150.0, 5.0);
	}
}
