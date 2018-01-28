package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;
import org.usfirst.frc.team2357.robot.subsystems.intake.states.IntakeStates;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	
	protected IntakeSub intakeSub;

    public IntakeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intakeSub = Robot.getInstance().getIntakeSubsystem());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(intakeSub.getState() == IntakeStates.closed){
    		intakeSub.getIntakeSolenoid().set(Value.kReverse);
    	}else if(intakeSub.getState() == IntakeStates.opening){
    		intakeSub.getIntakeSolenoid().set(Value.kForward);
    	}else if(intakeSub.getState() == IntakeStates.open){
    		intakeSub.getIntakeSolenoid().set(Value.kForward);
    	}else if(intakeSub.getState() == IntakeStates.closing){
    		intakeSub.getIntakeSolenoid().set(Value.kReverse);
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
