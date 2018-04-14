package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.AbstractStateCommandGroup;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.auto.AutonomousSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.auto.Cube1EndingPosition;
import org.usfirst.frc.team2357.robot.subsystems.auto.PlatformSide;
import org.usfirst.frc.team2357.robot.subsystems.auto.TargetPreference;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.ElevatorCommand;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.ManualElevatorCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub.IntakeState;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeOutCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeRaiseManual;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.SetManualIntake;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.TimedIntakeOutCommand;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Command for starting in the middle. For now this is switch only.
 */
public class StartMiddleSwitch extends AbstractStateCommandGroup {
	private AutonomousSubsystem as = Robot.getInstance().getAutonomousSubsystem();
	public StartMiddleSwitch() {
		PlatformSide switchSide = as.getSwitchSide();

		// This will start the intake dropping...
//		addSequential(new SetManualIntake(true));
//    	addParallel(new IntakeRaiseManual(-1), 2.0);
    	addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 3.0));
    	addSequential(new WaitCommand(1.0));
    	if(switchSide == PlatformSide.RIGHT){
    		addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, -60.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 0.0, 3.0));
    	} else if(switchSide == PlatformSide.LEFT){
    		addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 80.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 0.0, 3.0));
    	}
    	addSequential(new WaitCommand(1.0));
		addParallel(new ElevatorCommand(.8), 1.0);
    	addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, 70.0, 0.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 3.0));
    	addSequential(new IntakeOutCommand(.5), 1.0);
    	addSequential(new AutoDriveSegment(FixedIntakeDirection.UP_FIELD, -30.0, 0.0, AbstractStagedAutonomous.STANDARD_AUTO_SPEED, 3.0));
		// addCube2Commands(cube1EndingPosition);
	}
}
