package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub.IntakeState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeStateCommand extends Command {
	
	protected IntakeSub intakeSub = Robot.getInstance().getIntakeSubsystem();
	protected long location, deltaTime;

    public IntakeStateCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intakeSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		switch (intakeSub.getState()) {
		case RAISED:
			deltaTime = System.currentTimeMillis();
			break;
		case LOWERED:
			deltaTime = System.currentTimeMillis();
			break;
		case RAISING:
			intakeSub.raiseIntake();
			location += System.currentTimeMillis() - deltaTime;
			deltaTime = System.currentTimeMillis();
			if (location >= 1000) {
				intakeSub.setState(IntakeState.RAISED);
			}
		case LOWERING:
			intakeSub.lowerIntake();
			location -= System.currentTimeMillis() - deltaTime;
			deltaTime = System.currentTimeMillis();
			if (location <= 0) {
				intakeSub.setState(IntakeState.LOWERED);
			}
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
