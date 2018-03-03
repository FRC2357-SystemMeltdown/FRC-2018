package org.usfirst.frc.team2357.robot.subsystems.intake.commands;

import java.util.logging.Level;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub;
import org.usfirst.frc.team2357.robot.subsystems.intake.IntakeSub.IntakeState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeStateCommand extends Command {
	public static final long RAISED_LOCATION = 1000;
	public static final long LOWERED_LOCATION = 0;
	private IntakeSub intakeSub = Robot.getInstance().getIntakeSubsystem();
	private long location = RAISED_LOCATION;
	private long deltaTime = 0;

    public IntakeStateCommand() {
    	requires(intakeSub);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		switch (intakeSub.getState()) {
		case RAISED:
			deltaTime = System.currentTimeMillis();
			break;
		case LOWERED:
			deltaTime = System.currentTimeMillis();
			break;
		case RAISING:
			intakeSub.raiseIntake();
			location += (System.currentTimeMillis() - deltaTime);
			deltaTime = System.currentTimeMillis();
			if (location >= RAISED_LOCATION) {
				intakeSub.setState(IntakeState.RAISED);
				location = RAISED_LOCATION;
			}
			break;
		case LOWERING:
			intakeSub.lowerIntake();
			location -= (System.currentTimeMillis() - deltaTime);
			deltaTime = System.currentTimeMillis();
			if (location <= LOWERED_LOCATION) {
				intakeSub.setState(IntakeState.LOWERED);
				location = LOWERED_LOCATION;
			}
			break;
		default:
			Robot.getInstance().getLogger().log(Level.WARNING, "IntakeState not set.");
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
