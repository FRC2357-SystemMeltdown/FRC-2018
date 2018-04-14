package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.auto.Cube1EndingPosition;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.auto.TargetPreference;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub.IntakeState;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeRaiseManual;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.SetManualIntake;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.TimedIntakeOutCommand;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Command for starting in the middle. For now this is switch only.
 */
public class StartMiddle extends AbstractStagedAutonomous {
	public StartMiddle() {
		super();
		PlatformSide switchSide = as.getSwitchSide();
		PlatformSide scaleSide = as.getScaleSide();
		// TargetPreference tp = as.getTargetPreference();
		TargetPreference tp = TargetPreference.ALWAYS_SWITCH;
		Cube1EndingPosition cube1EndingPosition = null;

		// This will start the intake dropping...
		addSequential(new SetManualIntake(true));
    	addParallel(new IntakeRaiseManual(-1), 2.0);

		if (as.getAutoStartWaitTime() > 0.0) {
			addSequential(new WaitCommand(as.getAutoStartWaitTime()));
		}
		//addSequential(new GotoElevatorPositionCommand(Floors.CARRY));
		if ((switchSide == PlatformSide.RIGHT)
				&& (((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH))
						|| ((tp == TargetPreference.PREFER_SCALE) && (scaleSide == PlatformSide.LEFT)))) {
			// Got here if there are any conditions that lead to right switch.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, STANDARD_AUTO_SPEED, 1.0));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 60.0, STANDARD_AUTO_SPEED, 0.0, 3.0));
			//addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, STANDARD_AUTO_SPEED, 5.0));
			addSequential(new TimedIntakeOutCommand(2.0, 0.6));
			cube1EndingPosition = Cube1EndingPosition.FRONT_OF_RIGHT_SWITCH;
		} else if ((scaleSide == PlatformSide.RIGHT) && (tp != TargetPreference.ALWAYS_SWITCH)) {

		} else if ((tp == TargetPreference.ALWAYS_SWITCH) || (tp == TargetPreference.PREFER_SWITCH)) {
			// Got here if there are any conditions that lead to left switch.
			// TODO check these drive distances.
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, STANDARD_AUTO_SPEED, 1.0));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, -80.0, -STANDARD_AUTO_SPEED, 0.0, 3.0));
			//addParallel(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
			addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, STANDARD_AUTO_SPEED, 5.0));
			addSequential(new TimedIntakeOutCommand(2.0, 0.6));
			cube1EndingPosition = Cube1EndingPosition.FRONT_OF_LEFT_SWITCH;
		} else {

		}
		// addCube2Commands(cube1EndingPosition);
	}
}
