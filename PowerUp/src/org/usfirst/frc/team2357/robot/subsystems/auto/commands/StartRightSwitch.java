package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.auto.Cube1EndingPosition;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.auto.TargetPreference;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.TimedIntakeOutCommand;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Command for starting in front of the right switch plate.
 */
public class StartRightSwitch extends AbstractStagedAutonomous {
	public StartRightSwitch() {
		super();
		PlatformSide switchSide = as.getSwitchSide();
		PlatformSide scaleSide = as.getScaleSide();
		TargetPreference tp = as.getTargetPreference();
		Cube1EndingPosition cube1EndingPosition = null;

		if (as.getAutoStartWaitTime() > 0.0) {
			addSequential(new WaitCommand(as.getAutoStartWaitTime()));
		}
		// TODO add parallel drop intake
		addSequential(new GotoElevatorPositionCommand(Floors.CARRY));
		if ((switchSide == PlatformSide.RIGHT)
				&& (((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH))
						|| ((tp == TargetPreference.PREFER_SCALE) && (scaleSide == PlatformSide.LEFT)))) {
			// Got here if there are any conditions that lead to right switch.
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 120.0, 0.0, 0.7));
			addSequential(new TimedIntakeOutCommand(2.0));
			cube1EndingPosition = Cube1EndingPosition.FRONT_OF_RIGHT_SWITCH;
		} else if ((scaleSide == PlatformSide.RIGHT) && (tp != TargetPreference.ALWAYS_SWITCH)) {
			// Got here if there are any conditions that lead to right scale.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 20.0, 0.0, 0.7));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 60.0, 0.7, 0.0));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 100.0, 0.0, 0.7));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SCALE_THEY_OWN));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, 0.7));
			// TODO add command to drive intake out.
			cube1EndingPosition = Cube1EndingPosition.RIGHT_SCALE;
		} else if ((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH)) {
			// Got here if there are any conditions that lead to left switch.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 20.0, 0.0, 0.7));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 120.0, -0.7, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 120.0, 0.0, 0.7));
			// TODO add command to drive intake out.
			cube1EndingPosition = Cube1EndingPosition.FRONT_OF_LEFT_SWITCH;
		} else {
			// Got here if there are any conditions that lead to left scale.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 20.0, 0.0, 0.7));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 60.0, 0.7, 0.0));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 100.0, 0.0, 0.7));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 200.0, -0.7, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SCALE_THEY_OWN));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 40.0, 0.0, 0.7));
			// TODO add command to drive intake out.
			cube1EndingPosition = Cube1EndingPosition.LEFT_SCALE;
		}
		addCube2Commands(cube1EndingPosition);
	}
}
