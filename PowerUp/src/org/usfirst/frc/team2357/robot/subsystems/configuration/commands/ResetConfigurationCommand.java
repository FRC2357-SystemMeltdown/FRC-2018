package org.usfirst.frc.team2357.robot.subsystems.configuration.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Use with care as this will force the configuration information to be re-read
 * and the robot configuration will change. This should typically be attached to
 * a dash board or controller interaction that takes very deliberate action to
 * trigger. For example, one button on both controllers being pressed together.
 */
public class ResetConfigurationCommand extends Command {
	/**
	 * This command uses the {@link ConfigurationSubsystem}.
	 */
	public ResetConfigurationCommand() {
		requires(Robot.getInstance().getConfigurationSubsystem());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation resets the {@link ConfigurationSubsystem}.
	 * </p>
	 */
	@Override
	protected void initialize() {
		Robot.getInstance().getConfigurationSubsystem().reset();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation has nothing to do since {@link #finalize()} alredy
	 * reset the {@link ConfigurationSubsystem} and {@link #isFinished() will
	 * always return true.
	 * </p>
	 */
	@Override
	protected void execute() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This command is finished immediately.
	 * </p>
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation does nothing.
	 * </p>
	 */
	@Override
	protected void end() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation behaves the same as {@link #end()}.
	 * </p>
	 */
	@Override
	protected void interrupted() {
		end();
	}
}
