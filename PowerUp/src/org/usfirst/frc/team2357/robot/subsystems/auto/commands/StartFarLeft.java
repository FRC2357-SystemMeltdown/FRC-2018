package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.auto.Cube1EndingPosition;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.auto.TargetPreference;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub.IntakeState;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.TimedIntakeOutCommand;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Command for starting far left.
 */
public class StartFarLeft extends AbstractStagedAutonomous {
	public StartFarLeft() {
		super();
		PlatformSide switchSide = as.getSwitchSide();
		PlatformSide scaleSide = as.getScaleSide();
		TargetPreference tp = as.getTargetPreference();
		Cube1EndingPosition cube1EndingPosition = null;

		// This will start the intake dropping...
		Robot.getInstance().getIntakeSubsystem().setState(IntakeState.LOWERING);

		if (as.getAutoStartWaitTime() > 0.0) {
			addSequential(new WaitCommand(as.getAutoStartWaitTime()));
		}
		// TODO add parallel drop intake
		addSequential(new GotoElevatorPositionCommand(Floors.CARRY));
		if ((switchSide == PlatformSide.LEFT)
				&& (((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH))
						|| ((tp == TargetPreference.PREFER_SCALE) && (scaleSide == PlatformSide.RIGHT)))) {
			// Got here if there are any conditions that lead to left switch.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 140.0, 0.0, STANDARD_AUTO_SPEED));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 90.0, STANDARD_AUTO_SPEED, 0.0));
			addSequential(new TimedIntakeOutCommand(2.0, 0.6));
			cube1EndingPosition = Cube1EndingPosition.SIDE_OF_LEFT_SWITCH;
		} else if ((scaleSide == PlatformSide.LEFT) && (tp != TargetPreference.ALWAYS_SWITCH)) {
			// Got here if there are any conditions that lead to left scale.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 190.0, 0.0, STANDARD_AUTO_SPEED));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 60.0, STANDARD_AUTO_SPEED, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SCALE_THEY_OWN));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 50.0, 0.0, STANDARD_AUTO_SPEED));
			addSequential(new TimedIntakeOutCommand(2.0, 0.6));
			cube1EndingPosition = Cube1EndingPosition.LEFT_SCALE;
		} else if ((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH)) {
			// Got here if there are any conditions that lead to right switch.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 20.0, 0.0, STANDARD_AUTO_SPEED));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 120.0, STANDARD_AUTO_SPEED, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 120.0, 0.0, STANDARD_AUTO_SPEED));
			addSequential(new TimedIntakeOutCommand(2.0, 0.6));
			cube1EndingPosition = Cube1EndingPosition.FRONT_OF_RIGHT_SWITCH;
		} else {
			// Got here if there are any conditions that lead to right scale.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.RIGHT, 190.0, 0.0, STANDARD_AUTO_SPEED));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 200.0, STANDARD_AUTO_SPEED, 0.0));
			addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SCALE_THEY_OWN));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 40.0, 0.0, STANDARD_AUTO_SPEED));
			addSequential(new TimedIntakeOutCommand(2.0, 0.6));
			cube1EndingPosition = Cube1EndingPosition.RIGHT_SCALE;
		}
		// addCube2Commands(cube1EndingPosition);
	}
}
