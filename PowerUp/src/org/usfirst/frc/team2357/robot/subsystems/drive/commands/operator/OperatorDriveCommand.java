package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.OI;

/**
 * Gets the Cartesian X and Y drive values and any manual rotation value from
 * {@link OI}.
 */
public class OperatorDriveCommand extends AbstractOperatorDriveCommand {
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
