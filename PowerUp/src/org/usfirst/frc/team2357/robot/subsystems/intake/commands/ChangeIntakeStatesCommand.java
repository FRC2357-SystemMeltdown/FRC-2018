package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;
import org.usfirst.frc.team2357.robot.subsystems.intake.states.IntakeStates;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeIntakeStatesCommand extends Command {
	
	protected IntakeSub intakeSub;

    public ChangeIntakeStatesCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intakeSub = Robot.getInstance().getIntakeSubsystem());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch (intakeSub.getState()){
    	case closed: intakeSub.setState(IntakeStates.opening);
    	break;
    	case opening: intakeSub.setState(IntakeStates.open);
    	break;
    	case open: intakeSub.setState(IntakeStates.closing);
    	break;
    	case closing: intakeSub.setState(IntakeStates.closed);
    	break;
    	}	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
