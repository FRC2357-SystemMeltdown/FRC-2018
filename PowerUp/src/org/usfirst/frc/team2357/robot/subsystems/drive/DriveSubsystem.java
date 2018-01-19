package org.usfirst.frc.team2357.robot.subsystems.drive;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.OperatorDriveCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The {@link DriveSubsystem} is used by commands to drive the robot during both
 * autonomous and teleop. It owns all the drive motor controllers and drive
 * feedback sensors.
 */
public class DriveSubsystem extends Subsystem implements PIDOutput {
	public enum DriveMode {
		/**
		 * Cartesian movement is relative to the front of the robot with the X and Y
		 * components derived from the position of the movement stick. Rotation is
		 * proportional to the X axis deflection of the rotation stick. There is no PID
		 * control.
		 */
		ROBOT_RELATIVE,

		/**
		 * Cartesian movement is relative to the field with the X and Y components
		 * derived from the position of the movement stick. The PID controlled
		 * rotational target angle is derived from D-pad ordinal input or non-PID
		 * controlled X deflection of the rotation stick.
		 */
		FIELD_RELATIVE,

		/**
		 * Cartesian movement is relative to the field with the X and Y components
		 * explicitly fed from an autonomous routine. The PID controlled rotational
		 * target angle is explicitly fed from an autonomous routine.
		 */
		AUTO;
	}

	private final WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_MOTOR);
	private final WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_MOTOR);
	private final WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(RobotMap.BACK_LEFT_MOTOR);
	private final WPI_TalonSRX backRightMotor = new WPI_TalonSRX(RobotMap.BACK_RIGHT_MOTOR);
	private final MecanumDrive mecanumDrive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor,
			backRightMotor);

	private final DriveProperties props = new DriveProperties();

	private DriveMode driveMode = DriveMode.AUTO;
	private double autoRotationTargetAngle = 0.0;

	private boolean stickRotationDetected = false;
	private double autoOrDpadRotationAngle = 0.0;

	private AHRS gyro = new AHRS(Port.kUSB);

	private final PIDController rotationController;
	// TODO add pid controller for gyro turns
	// TODO always enabled
	// TODO auto or D pad input for ordinal directions
	// TODO pid keeps us on latest
	// TODO if rot stick input ignore pid output
	// TODO do not use pid output again until new input from D pad
	// TODO rot stick input rotates based on X deflection

	/**
	 * Registers with the configuration subsystem (drive properties set at this
	 * time), sets up the turn controller (this subsystem acting as a PID source and
	 * output). It also initializes the drive controllers.
	 */
	public DriveSubsystem() {
		super();
		Robot.getInstance().getConfigurationSubsystem().addConsumer(this.props);

		rotationController = new PIDController(props.rotatePIDp, props.rotatePIDi, props.rotatePIDd, 0.0, this.gyro,
				this, 0.01);
		rotationController.setInputRange(-180.0, 180.0);
		rotationController.setOutputRange(-1.0, 1.0);
		rotationController.setAbsoluteTolerance(props.rotatePIDTolerance);
		rotationController.setContinuous(true);
	}

	/**
	 * The operator drive control mode is configurable. See
	 * {@link DriveProperties#DRIVE_MODE_KEY} for details.
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new OperatorDriveCommand());
	}

	/**
	 * Used to stop driving. Usually from command end methods.
	 */
	public void stop() {
		this.mecanumDrive.stopMotor();
	}

	/**
	 * @return the current {@link DriveMode}.
	 */
	public DriveMode getDriveMode() {
		return this.driveMode;
	}

	/**
	 * @param driveMode
	 *            the new {@link DriveMode} setting.
	 */
	public void setDriveMode(final DriveMode driveMode) {
		this.driveMode = driveMode;
	}

	/**
	 * @return true if robot motion is field relative and false if robot relative.
	 */
	public boolean isFieldRelative() {
		return this.getDriveMode() != DriveMode.ROBOT_RELATIVE;
	}

	/**
	 * @return true if rotation uses gyro and pid and false if x magnitude only.
	 */
	public boolean isGyroRotation() {
		return this.getDriveMode() != DriveMode.ROBOT_RELATIVE;
	}

	/**
	 * @return true if the gyro guided rotation is fixed and not stick derived.
	 */
	public boolean isFixedTargetRotation() {
		return this.getDriveMode() == DriveMode.AUTO;
	}

	/**
	 * Angles are measured clockwise from the positive X axis. The robot's speed is
	 * independent from its angle or rotation rate.
	 * 
	 * <p>
	 * This signature uses the subsystem rotation value.
	 * </p>
	 *
	 * @param ySpeed
	 *            The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
	 * @param xSpeed
	 *            The robot's speed along the X axis [-1.0..1.0]. Forward is
	 *            positive.
	 */
	public void cartesianDrive(double ySpeed, double xSpeed) {
		this.mecanumDrive.driveCartesian(ySpeed, xSpeed, getRotationValue(),
				this.isFieldRelative() ? getGyroAngle() : 0.0);
	}

	public double getRotationValue() {
		// TODO implement gyro pid
		return this.isGyroRotation() ? /* TODO latest pid out */ 0.0 : Robot.getInstance().getOI().getRotationX();
	}

	//public double getRotationTargetAngle() {
	//	return isFixedTargetRotation() ? this.autoRotationTargetAngle : Robot.getInstance().getOI().getRotationAngle();
	//}

	/**
	 * @return the current gyro angle.
	 */
	public double getGyroAngle() {
		// TODO read the gyro
		return 0.0;
	}

	public DriveMode getInitialTeleDriveMode() {
		return props.initialTeleDriveMode;
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub

	}

	/**
	 * An instance of this class is used by the {@link DriveSubsystem} to manage the
	 * drive properties using the {@link ConfigurationSubsystem}.
	 */
	public class DriveProperties implements ConfigurationPropertiesConsumer {
		/**
		 * The value must be one of:
		 * <table>
		 * <tr>
		 * <th>Value</th>
		 * <th>Drive mode</th>
		 * </tr>
		 * <tr>
		 * <th>0</th>
		 * <th>Field Relative</th>
		 * </tr>
		 * <tr>
		 * <th>1</th>
		 * <th>Robot Relative</th>
		 * </tr>
		 * <tr>
		 * <th>other</th>
		 * <th>Field Relative</th>
		 * </tr>
		 * </table>
		 */
		public static final String INITIAL_TELE_DRIVE_MODE_KEY = "initial.tele.drive.mode";
		public final int INITIAL_TELE_DRIVE_MODE_VALUE_DEFAULT = 0;
		private DriveMode initialTeleDriveMode = DriveMode.FIELD_RELATIVE;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String ROTATE_PID_TOLERANCE_KEY = "drive.rotate.pid.tolerance";
		public static final double ROTATE_PID_TOLERANCE_DEFAULT = 1.0;
		private double rotatePIDTolerance = ROTATE_PID_TOLERANCE_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String ROTATE_PID_P_KEY = "drive.rotate.pid.p";
		public static final double ROTATE_PID_P_DEFAULT = 0.03;
		private double rotatePIDp = ROTATE_PID_P_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String ROTATE_PID_I_KEY = "drive.rotate.pid.i";
		public static final double ROTATE_PID_I_DEFAULT = 0.0;
		private double rotatePIDi = ROTATE_PID_I_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String ROTATE_PID_D_KEY = "drive.rotate.pid.d";
		public static final double ROTATE_PID_D_DEFAULT = 0.0;
		private double rotatePIDd = ROTATE_PID_D_DEFAULT;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void reset(ConfigurationSubsystem config) {
			this.initialTeleDriveMode = ConfigurationUtilities.getProperty(config, INITIAL_TELE_DRIVE_MODE_KEY,
					INITIAL_TELE_DRIVE_MODE_VALUE_DEFAULT) != 1 ? DriveMode.FIELD_RELATIVE : DriveMode.ROBOT_RELATIVE;

			this.rotatePIDTolerance = ConfigurationUtilities.getProperty(config, ROTATE_PID_TOLERANCE_KEY,
					ROTATE_PID_TOLERANCE_DEFAULT);

			this.rotatePIDp = ConfigurationUtilities.getProperty(config, ROTATE_PID_P_KEY, ROTATE_PID_P_DEFAULT);
			this.rotatePIDi = ConfigurationUtilities.getProperty(config, ROTATE_PID_I_KEY, ROTATE_PID_I_DEFAULT);
			this.rotatePIDd = ConfigurationUtilities.getProperty(config, ROTATE_PID_D_KEY, ROTATE_PID_D_DEFAULT);
		}
	}
}
