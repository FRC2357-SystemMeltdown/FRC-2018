package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedIntakeOutCommand extends TimedCommand {

	protected IntakeSub intakeSub;

	public TimedIntakeOutCommand(double timeoutSeconds) {
		super(timeoutSeconds);
		requires(intakeSub = Robot.getInstance().getIntakeSubsystem());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intakeSub.intakeOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		intakeSub.stop();
	}
}
