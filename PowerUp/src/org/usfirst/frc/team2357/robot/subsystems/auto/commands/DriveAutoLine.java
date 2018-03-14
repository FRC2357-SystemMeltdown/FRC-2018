package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;

/**
 * A simple autonomous command that just drives over the AUTO LINE for 5 points.
 */
public class DriveAutoLine extends AbstractStateCommandGroup {
	public DriveAutoLine() {
		addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 150.0, 0.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 5.0));
	}
}
