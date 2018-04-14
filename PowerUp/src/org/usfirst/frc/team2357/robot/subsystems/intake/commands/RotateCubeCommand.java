package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateCubeCommand extends Command {

	private final IntakeSub intakeSub;
	private final double speed;

	public RotateCubeCommand(double speed) {
		requires(intakeSub = Robot.getInstance().getIntakeSubsystem());
		this.speed = speed;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intakeSub.intakeRotate(this.speed);
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
