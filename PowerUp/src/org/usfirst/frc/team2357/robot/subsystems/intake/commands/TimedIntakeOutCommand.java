package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedIntakeOutCommand extends TimedCommand {

	private final IntakeSub intakeSub;
	private final double speed;

	public TimedIntakeOutCommand(double timeoutSeconds, double speed) {
		super(timeoutSeconds);
		requires(intakeSub = Robot.getInstance().getIntakeSubsystem());
		this.speed = speed;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intakeSub.intakeOut(this.speed);
	}

	// Called once after isFinished returns true
	protected void end() {
		intakeSub.stop();
	}
}
