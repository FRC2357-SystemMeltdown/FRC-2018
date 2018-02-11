package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.auto.AutonomousSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.auto.TargetPreference;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Command for starting far left.
 */
public class StartFarLeft extends AbstractStateCommandGroup {
	public StartFarLeft() {
		AutonomousSubsystem as = Robot.getInstance().getAutonomousSubsystem();
		PlatformSide switchSide = as.getSwitchSide();
		PlatformSide scaleSide = as.getScaleSide();
		TargetPreference tp = as.getTargetPreference();

		if (as.getAutoStartWaitTime() > 0.0) {
			addSequential(new WaitCommand(as.getAutoStartWaitTime()));
		}
		// TODO add parallel drop intake
		addSequential(new GotoElevatorPositionCommand(Floors.CARRY));
		if ((switchSide == PlatformSide.LEFT)
				&& (((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH))
						|| ((tp == TargetPreference.PREFER_SCALE) && (scaleSide == PlatformSide.RIGHT)))) {
			// Got here if there are any conditions that lead to left switch.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 140.0, 0.0, 0.7));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 90.0, 0.7, 0.0));
			// TODO add command to drive intake out.
			// TODO find a second cube and pick a target.
		} else if ((scaleSide == PlatformSide.LEFT) && (tp != TargetPreference.ALWAYS_SWITCH)) {
			// Got here if there are any conditions that lead to left scale.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 280.0, 0.0, 0.7));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SCALE_THEY_OWN));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 30.0, 0.7, 0.0));
			// TODO add command to drive intake out.
			// TODO find a second cube and pick a target.
		} else if ((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH)) {
			// Got here if there are any conditions that lead to right switch.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 20.0, 0.0, 0.7));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 100.0, 0.7, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 120.0, 0.0, 0.7));
			// TODO add command to drive intake out.
			// TODO find a second cube and pick a target.
		} else {
			// Got here if there are any conditions that lead to right scale.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 190.0, 0.0, 0.7));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 100.0, 0.7, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 40.0, 0.0, 0.7));
			// TODO add command to drive intake out.
			// TODO find a second cube and pick a target.
		}
	}
}
