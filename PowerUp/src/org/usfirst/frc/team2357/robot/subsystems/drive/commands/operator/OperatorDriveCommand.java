package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.OI;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.AbstractDriveCommand;

/**
 * Gets the Cartesian X and Y drive values from {@link OI}.
 */
public class OperatorDriveCommand extends AbstractDriveCommand {
	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the Y value from {@link OI}.
	 * </p>
	 */
	@Override
	protected double getNextY() {
		return oi.getYMoveValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the X value from {@link OI}.
	 * </p>
	 */
	@Override
	protected double getNextX() {
		return oi.getXMoveValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation returns the rotation stick X value from {@link OI}.
	 * </p>
	 */
	@Override
	protected double getNextStickManualRotation() {
		return oi.getRotationX();
	}
	
}
