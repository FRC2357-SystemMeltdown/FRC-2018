package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.auto.AutonomousSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.auto.TargetPreference;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;

/**
 * Command for starting far left.
 */
public class StartFarLeft extends AbstractStateCommandGroup {
	public StartFarLeft() {
		AutonomousSubsystem as = Robot.getInstance().getAutonomousSubsystem();
		PlatformSide switchSide = as.getSwitchSide();
		PlatformSide scaleSide = as.getScaleSide();
		TargetPreference tp = as.getTargetPreference();
		if ((switchSide == PlatformSide.LEFT)
				&& ((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH))) {
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 140.0, 0.0, 0.7));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 90.0, 0.7, 0.0));
			// TODO add command to drive intake out.
		} else {
			// TODO Other stuff
		}
	}
}
