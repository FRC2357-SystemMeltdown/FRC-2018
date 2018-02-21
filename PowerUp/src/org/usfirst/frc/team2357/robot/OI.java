package org.usfirst.frc.team2357.robot;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.ChangeDirectionDown;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.ChangeDirectionLeft;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.ChangeDirectionRight;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.auto.ChangeDirectionUp;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ToggleDriveModeCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ZeroGyroCommand;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem.Floors;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.GotoElevatorPositionCommand;
import org.usfirst.frc.team2357.robot.subsystems.elevator.commands.ManualElevatorCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeInCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeOutCommand;
import org.usfirst.frc.team2357.robot.triggers.DPadDownTrigger;
import org.usfirst.frc.team2357.robot.triggers.DPadUpTrigger;
import org.usfirst.frc.team2357.robot.triggers.TwoButtonTrigger;

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
//	private DPadUpTrigger up;
//	private DPadRightTrigger right;
//	private DPadDownTrigger down;
//	private DPadLeftTrigger left;
	
	private XboxController coController;
	private Button floor;
	private Button carry;
	private Button scoreSwitch;
	private Button scoreScale;
	private Button climb;
	private Button manualElevator;
	private Button coBackButton;
	private DPadDownTrigger intakeIn;
	private DPadUpTrigger intakeOut;

	private TwoButtonTrigger resetGyro;

	private static final double STICK_ROTATION_MIN_DEFLECTION = 0.1;
	private static final double STICK_ELEVATOR_MIN_DEFLECTION = 0.1;

	public OI() {
		driveController = new XboxController(0);
		a = new JoystickButton(getDriveController(), 1);
		b = new JoystickButton(getDriveController(), 2);
		x = new JoystickButton(getDriveController(), 3);
		y = new JoystickButton(getDriveController(), 4);
		leftBumper = new JoystickButton(getDriveController(), 5);
		rightBumper = new JoystickButton(getDriveController(), 6);
		// DO NOT USE backButton
		backButton = new JoystickButton(getDriveController(), 7);
		startButton = new JoystickButton(getDriveController(), 8);
		leftStickButton = new JoystickButton(getDriveController(), 9);
		rightStickButton = new JoystickButton(getDriveController(), 10);
//		up = new DPadUpTrigger(driveController);
//		right = new DPadRightTrigger(driveController);
//		down = new DPadDownTrigger(driveController);
//		left = new DPadLeftTrigger(driveController);

		coController = new XboxController(1);
		floor = new JoystickButton(getCoController(), 1); // A
		carry = new JoystickButton(getCoController(), 3); // X
		scoreSwitch = new JoystickButton(getCoController(), 2); // B
		scoreScale = new JoystickButton(getCoController(), 4); // Y
		climb = new JoystickButton(getCoController(), 6); // Right bumper
		manualElevator = new JoystickButton(getCoController(), 5); // Left bumper
		intakeIn = new DPadDownTrigger(coController);
		intakeOut = new DPadUpTrigger(coController);
		// DO NOT USE coBackButton
		coBackButton = new JoystickButton(getCoController(), 7);

		// Press both back buttons together to reset gyro.
		resetGyro = new TwoButtonTrigger(backButton, coBackButton);
	}
	
	public void initCommands(){
		startButton.whenPressed(new ToggleDriveModeCommand());
//		startButton.whenPressed(new ChangeDriveModeFieldCommand());
//		up.whenActive(new ChangeDriveModeForwardCommand());
//		right.whenActive(new ChangeDriveModeRightCommand());
//		down.whenActive(new ChangeDriveModeBackCommand());
//		left.whenActive(new ChangeDriveModeLeftCommand());
		a.whenPressed(new ChangeDirectionDown());
		b.whenPressed(new ChangeDirectionRight());
		x.whenPressed(new ChangeDirectionLeft());
		y.whenPressed(new ChangeDirectionUp());

		floor.whenPressed(new GotoElevatorPositionCommand(Floors.FLOOR));
		carry.whenPressed(new GotoElevatorPositionCommand(Floors.CARRY));
		scoreSwitch.whenPressed(new GotoElevatorPositionCommand(Floors.SCORE_SWITCH));
		scoreScale.whenPressed(new GotoElevatorPositionCommand(Floors.SCORE_SCALE_THEY_OWN));
		climb.whenPressed(new GotoElevatorPositionCommand(Floors.CLIMB_INITIATION));
		manualElevator.whileHeld(new ManualElevatorCommand());
		intakeIn.whileActive(new IntakeInCommand(0.7));
		intakeOut.whileActive(new IntakeOutCommand(0.6));

		resetGyro.whenActive(new ZeroGyroCommand());
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
		return cubedInputs(speed);
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
		return cubedInputs(speed);
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
		return cubedInputs(rotation);
	}

	public XboxController getDriveController() {
		return driveController;
	}

	public XboxController getCoController() {
		return coController;
	}

	/**
	 * @return the manual elevator speed. The value is between -1.0 and 1.0.
	 */
	public double getManualElevatorSpeed() {
		double speed = getCoController().getY(Hand.kRight);
		if (Math.abs(speed) < STICK_ELEVATOR_MIN_DEFLECTION) {
			speed = 0.0;
		}
		return cubedInputs(speed);
	}

	private static double cubedInputs(double raw) {
		return Math.pow(raw, 3.0);
	}
}
