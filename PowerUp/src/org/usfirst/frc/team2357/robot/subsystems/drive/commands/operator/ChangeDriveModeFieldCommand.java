package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.DriveMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeDriveModeFieldCommand extends Command {
	
	protected DriveSubsystem driveSub;

    public ChangeDriveModeFieldCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(driveSub = Robot.getInstance().getDriveSubsystem());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveSub.setDriveMode(DriveMode.FIELD_RELATIVE);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}
