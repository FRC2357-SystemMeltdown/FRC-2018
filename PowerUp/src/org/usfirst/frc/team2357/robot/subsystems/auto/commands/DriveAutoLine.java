package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.AutoDriveStraightOnAxis;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A simple autonomous command that just drives over the AUTO LINE for 5 points.
 */
public class DriveAutoLine extends CommandGroup {
	public DriveAutoLine() {
		addSequential(new AutoDriveStraightOnAxis(0.3, true, 5000));
	}
}
