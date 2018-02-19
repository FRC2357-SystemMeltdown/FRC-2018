package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeOutCommand extends Command {

	protected IntakeSub intakeSub;

	public IntakeOutCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(intakeSub = Robot.getInstance().getIntakeSubsystem());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intakeSub.intakeOut();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		intakeSub.stop();
	}
}
