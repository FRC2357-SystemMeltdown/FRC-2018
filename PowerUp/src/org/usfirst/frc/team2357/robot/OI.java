package org.usfirst.frc.team2357.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Joystick moveStick = new Joystick(RobotMap.MOVE_STICK);
	Joystick rotateStick = new Joystick(RobotMap.ROTATE_STICK);

	public OI() {
	}

	/**
	 * @return the Cartesian movement value in the Y direction. The value is between
	 *         -1.0 and 1.0 where 0.0 is no Y movement.
	 */
	public double getYMoveValue() {
		return moveStick.getY();
	}

	/**
	 * @return the Cartesian movement value in the X direction. The value is between
	 *         -1.0 and 1.0 where 0.0 is no X movement.
	 */
	public double getXMoveValue() {
		return moveStick.getX();
	}

	/**
	 * @return the manual rotation value. The value is between -1.0 and 1.0 where
	 *         0.0 is no rotation.
	 */
	public double getRotationX() {
		return rotateStick.getX();
	}

	/**
	 * @return the field relative rotation target angle based on user input. The
	 *         value is between -pi and pi radians where 0.0 is to right in on the X
	 *         axis. This value will likely need to be rotated depending on the
	 *         physical orientation of the gyro on the robot.
	 */
	public double getRotationAngle() {
		// TODO check stick inputs, gyro 0 direction and rework this math.
		return Math.atan2(-rotateStick.getY(), rotateStick.getX());
	}
}
