package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeRaiseManual;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeStateCycleCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.SetManualIntake;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.ToggleManualIntake;

/**
 * A simple autonomous command that just drives over the AUTO LINE for 5 points.
 */
public class DriveAutoLine extends AbstractStateCommandGroup {
	public DriveAutoLine() {
		addSequential(new SetManualIntake(true));
		addParallel(new IntakeRaiseManual(-1), 5.5);
		addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 180.0, 0.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 5.0));
	}
}
