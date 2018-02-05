package org.usfirst.frc.team2357.robot;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ChangeDriveModeBackCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ChangeDriveModeFieldCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ChangeDriveModeForwardCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ChangeDriveModeLeftCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ChangeDriveModeRightCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ZeroGyroCommand;
import org.usfirst.frc.team2357.robot.triggers.DPadDownTrigger;
import org.usfirst.frc.team2357.robot.triggers.DPadLeftTrigger;
import org.usfirst.frc.team2357.robot.triggers.DPadRightTrigger;
import org.usfirst.frc.team2357.robot.triggers.DPadUpTrigger;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private XboxController driveController;
	private Button a;
	private Button b;
	private Button x;
	private Button y;
	private Button leftBumper;
	private Button rightBumper;
	private Button backButton;
	private Button startButton;
	private Button leftStickButton;
	private Button rightStickButton;
	private DPadUpTrigger up;
	private DPadRightTrigger right;
	private DPadDownTrigger down;
	private DPadLeftTrigger left;

	private static final double STICK_ROTATION_MIN_DEFLECTION = 0.1;

	public OI() {
		driveController = new XboxController(0);
		a = new JoystickButton(getDriveController(), 1);
		b = new JoystickButton(getDriveController(), 2);
		x = new JoystickButton(getDriveController(), 3);
		y = new JoystickButton(getDriveController(), 4);
		leftBumper = new JoystickButton(getDriveController(), 5);
		rightBumper = new JoystickButton(getDriveController(), 6);
		backButton = new JoystickButton(getDriveController(), 7);
		startButton = new JoystickButton(getDriveController(), 8);
		leftStickButton = new JoystickButton(getDriveController(), 9);
		rightStickButton = new JoystickButton(getDriveController(), 10);
		up = new DPadUpTrigger();
		right = new DPadRightTrigger();
		down = new DPadDownTrigger();
		left = new DPadLeftTrigger();
	}
	
	public void initCommands(){
		startButton.whenPressed(new ChangeDriveModeFieldCommand());
		backButton.whenPressed(new ZeroGyroCommand());
		up.whenActive(new ChangeDriveModeForwardCommand());
		right.whenActive(new ChangeDriveModeRightCommand());
		down.whenActive(new ChangeDriveModeBackCommand());
		left.whenActive(new ChangeDriveModeLeftCommand());
	}

	/**
	 * @return the Cartesian movement value in the Y direction. The value is between
	 *         -1.0 and 1.0 where 0.0 is no Y movement.
	 */
	public double getYMoveValue() {
		double speed = -getDriveController().getY(Hand.kLeft);
		if (Math.abs(speed) < STICK_ROTATION_MIN_DEFLECTION) {
			speed = 0.0;
		}
		return speed;
	}

	/**
	 * @return the Cartesian movement value in the X direction. The value is between
	 *         -1.0 and 1.0 where 0.0 is no X movement.
	 */
	public double getXMoveValue() {
		double speed = -getDriveController().getX(Hand.kLeft);
		if (Math.abs(speed) < STICK_ROTATION_MIN_DEFLECTION) {
			speed = 0.0;
		}
		return speed;
	}

	/**
	 * @return the manual rotation value. The value is between -1.0 and 1.0 where
	 *         0.0 is no rotation.
	 */
	public double getRotationX() {
		double rotation = getDriveController().getX(Hand.kRight);
		if (Math.abs(rotation) < STICK_ROTATION_MIN_DEFLECTION) {
			rotation = 0.0;
		}
		return rotation;
	}

	public XboxController getDriveController() {
		return driveController;
	}

	/**
	 * @return the field relative rotation target angle based on user input. The
	 *         value is between -pi and pi radians where 0.0 is to right in on the X
	 *         axis. This value will likely need to be rotated depending on the
	 *         physical orientation of the gyro on the robot.
	 */
}
