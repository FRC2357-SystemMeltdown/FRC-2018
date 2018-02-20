package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.DriveMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleDriveModeCommand extends Command {
	
	protected DriveSubsystem driveSub;

    public ToggleDriveModeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(driveSub = Robot.getInstance().getDriveSubsystem());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriveMode mode = driveSub.getDriveMode();
    	driveSub.setDriveMode(mode == DriveMode.FIELD_RELATIVE ? DriveMode.ROBOT_RELATIVE : DriveMode.FIELD_RELATIVE);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}
