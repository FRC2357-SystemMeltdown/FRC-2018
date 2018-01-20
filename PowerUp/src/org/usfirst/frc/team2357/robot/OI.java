package org.usfirst.frc.team2357.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private XboxController driveController = new XboxController(0);
	private Button a = new JoystickButton(driveController, 1);
	private Button b = new JoystickButton(driveController, 2);
	private Button x = new JoystickButton(driveController, 3);
	private Button y = new JoystickButton(driveController, 4);
	private Button leftBumper = new JoystickButton(driveController, 5);
	private Button rightBumper = new JoystickButton(driveController, 6);
	private Button backButton = new JoystickButton(driveController, 7);
	private Button startButton = new JoystickButton(driveController, 8);
	private Button leftStickButton = new JoystickButton(driveController, 9);
	private Button rightStickButton = new JoystickButton(driveController, 10);

	private static final double STICK_ROTATION_MIN_DEFLECTION = 0.1;

	public OI() {
	}

	/**
	 * @return the Cartesian movement value in the Y direction. The value is between
	 *         -1.0 and 1.0 where 0.0 is no Y movement.
	 */
	public double getYMoveValue() {
		return driveController.getY(Hand.kLeft);
	}

	/**
	 * @return the Cartesian movement value in the X direction. The value is between
	 *         -1.0 and 1.0 where 0.0 is no X movement.
	 */
	public double getXMoveValue() {
		return driveController.getX(Hand.kLeft);
	}

	/**
	 * @return the manual rotation value. The value is between -1.0 and 1.0 where
	 *         0.0 is no rotation.
	 */
	public double getRotationX() {
		double rotation = driveController.getX(Hand.kRight);
		if (Math.abs(rotation) < STICK_ROTATION_MIN_DEFLECTION) {
			rotation = 0.0;
		}
		return rotation;
	}

	/**
	 * @return the field relative rotation target angle based on user input. The
	 *         value is between -pi and pi radians where 0.0 is to right in on the X
	 *         axis. This value will likely need to be rotated depending on the
	 *         physical orientation of the gyro on the robot.
	 */
}
