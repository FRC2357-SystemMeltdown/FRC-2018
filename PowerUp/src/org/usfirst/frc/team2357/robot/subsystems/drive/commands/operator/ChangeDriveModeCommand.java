package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.DriveMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeDriveModeCommand extends Command {
	
	protected DriveSubsystem driveSub;

    public ChangeDriveModeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(driveSub = Robot.getInstance().getDriveSubsystem());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(driveSub.getDriveMode() == DriveMode.FIELD_RELATIVE){
    		driveSub.setDriveMode(DriveMode.ROBOT_RELATIVE);
    	} else {
    		driveSub.setDriveMode(DriveMode.FIELD_RELATIVE);
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