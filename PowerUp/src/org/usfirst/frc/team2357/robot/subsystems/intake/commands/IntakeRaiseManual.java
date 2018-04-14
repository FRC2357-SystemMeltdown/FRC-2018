package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;

/**
 *
 */
public class IntakeRaiseManual extends Command {
	
	protected IntakeSub intakeSub = Robot.getInstance().getIntakeSubsystem();
	private int speed;

    public IntakeRaiseManual(int speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intakeSub);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!intakeSub.isStateEnabled()){
    		intakeSub.setDropMotor(speed);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intakeSub.setDropMotor(0);
    }
}
