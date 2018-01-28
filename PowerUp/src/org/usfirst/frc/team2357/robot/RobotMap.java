package org.usfirst.frc.team2357.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Joystick port values.
	public static final int MOVE_STICK = 0;
	public static final int ROTATE_STICK = 1;

	// Drive talon CAN bus ports.
	public static final int FRONT_LEFT_MOTOR = 4;
	public static final int FRONT_RIGHT_MOTOR = 3;
	public static final int BACK_LEFT_MOTOR = 1;
	public static final int BACK_RIGHT_MOTOR = 2;

	// Elevator talon CAN bus port and limit switch DIOs.
	public static final int ELEVATOR_MOTOR = 5;
	// TODO do we want two limit switches here or direct to the Talon?

}
