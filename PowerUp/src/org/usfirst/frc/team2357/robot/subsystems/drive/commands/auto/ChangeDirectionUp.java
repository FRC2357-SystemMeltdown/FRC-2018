package org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.FixedIntakeDirection;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeDirectionUp extends Command {
	
	private DriveSubsystem driveSub = Robot.getInstance().getDriveSubsystem();

    public ChangeDirectionUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(driveSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveSub.setFixedIntakeDirection(FixedIntakeDirection.UP_FIELD);
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
