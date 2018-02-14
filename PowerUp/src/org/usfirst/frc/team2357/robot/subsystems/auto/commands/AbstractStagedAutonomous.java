package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.auto.AutonomousSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.auto.Cube1EndingPosition;
import org.usfirst.frc.team2357.robot.subsystems.auto.CubeTwoOptions;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * A base class for autonomous commands that might go for more than one cube.
 */
public abstract class AbstractStagedAutonomous extends AbstractStateCommandGroup {
	protected final AutonomousSubsystem as;

	public AbstractStagedAutonomous() {
		as = Robot.getInstance().getAutonomousSubsystem();
	}

	protected void addCube2Commands(Cube1EndingPosition cube1EndingPosition) {
		CubeTwoOptions cubeTwoOption = as.getCubeTwoOption();
		if (cubeTwoOption != CubeTwoOptions.NO_CUBE_TWO) {
			if (as.getAutoPostScoreFirstCubeWaitTime() > 0.0) {
				addSequential(new WaitCommand(as.getAutoPostScoreFirstCubeWaitTime()));
			}
			switch (cube1EndingPosition) {
			case SIDE_OF_LEFT_SWITCH:
				addSequential(new AutoDriveSegment(FixedIntakeDirection.DOWN_FIELD, 40.0, -0.7, 0.7));
				addParallel(new GotoElevatorPositionCommand(Floors.CARRY));
				addSequential(new AutoDriveSegment(FixedIntakeDirection.DOWN_FIELD, 40.0, 0.7, 0.0));
				// TODO add command to detect and center on cube.
				addParallel(new GotoElevatorPositionCommand(Floors.FLOOR));
				// TODO add parallel intake inward.
				addSequential(new AutoDriveSegment(FixedIntakeDirection.DOWN_FIELD, 30.0, 0.0, -0.7));
				if (cubeTwoOption == CubeTwoOptions.SAME) {
					addSequential(new AutoDriveSegment(FixedIntakeDirection.DOWN_FIELD, 20.0, 0.7, 0.7));
					addSequential(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
					addSequential(new AutoDriveSegment(FixedIntakeDirection.DOWN_FIELD, 30.0, 0.0, -0.7));
					// TODO add sequential command for intake outward.
				} else {
					addSequential(new AutoDriveSegment(FixedIntakeDirection.DOWN_FIELD, 20.0, 0.0, 0.7));
					if (as.getScaleSide() == PlatformSide.LEFT) {
						// TODO do the steps to score left scale from here.
					} else {
						// TODO do the steps to score right scale from here.
					}
				}
				break;
			default:
				break;
			}
		}
	}
}
