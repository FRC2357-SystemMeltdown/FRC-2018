package org.usfirst.frc.team2357.robot.subsystems.drive;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.OperatorDriveCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The {@link DriveSubsystem} is used by commands to drive the robot during both
 * autonomous and teleop. It owns all the drive motor controllers and drive
 * feedback sensors.
 */
public class DriveSubsystem extends Subsystem implements PIDOutput {
	/**
	 * Values determine the Cartesian movement of the robot. Rotation is derived
	 * separately depending on user input of fixed headings or manual rotation stick
	 * input.
	 */
	public enum DriveMode {
		/**
		 * Cartesian movement is relative to the front of the robot with the X and Y
		 * components derived from the position of the movement stick.
		 */
		ROBOT_RELATIVE,

		/**
		 * Cartesian movement is relative to the field with the X and Y components
		 * derived from the position of the movement stick.
		 */
		FIELD_RELATIVE;
	}

	/**
	 * Driver station view relative orientations for the intake.
	 */
	public enum FixedIntakeDirection {
		/**
		 * Intake up field away from our driver station.
		 */
		UP_FIELD(0.0),

		/**
		 * Intake toward the right of the field.
		 */
		RIGHT(90.0),

		/**
		 * Intake down field toward our driver station.
		 */
		DOWN_FIELD(180.0),

		/**
		 * Intake toward the left of the field.
		 */
		LEFT(-90.0);

		private final double heading;

		private FixedIntakeDirection(double heading) {
			this.heading = heading;
		}

		/**
		 * Return the desired gyro angle for this heading.
		 * 
		 * @return return the desired gyro angle for this heading.
		 */
		public double getHeading() {
			return this.heading;
		}
	}

	private final WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_MOTOR);
	private final WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_MOTOR);
	private final WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(RobotMap.BACK_LEFT_MOTOR);
	private final WPI_TalonSRX backRightMotor = new WPI_TalonSRX(RobotMap.BACK_RIGHT_MOTOR);
	private final MecanumDrive mecanumDrive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor,
			backRightMotor);

	private final DriveProperties props = new DriveProperties();

	/*
	 * Initialize for tele-op. Autonomous mode will set as needed. Autonomous will
	 * end by setting field relative and its last fixed heading.
	 * 
	 * We cannot initialize here for auto since the proper fixed heading could be
	 * different for each one.
	 */
	private DriveMode driveMode = DriveMode.FIELD_RELATIVE;
	private boolean stickRotationDetected = true;
	private FixedIntakeDirection fixedIntakeDirection = null;
	private double lastPIDOutput = 0.0;

	private AHRS gyro = new AHRS(I2C.Port.kOnboard);

	private final PIDController rotationController;

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
		rotationController.setOutputRange(-0.7, 0.7);
		rotationController.setAbsoluteTolerance(props.rotatePIDTolerance);
		rotationController.setContinuous(true);
	}

	/**
	 * {@inheritDoc}
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
	 * Returns the current {@link FixedIntakeDirection} which could be null if
	 * manually turning.
	 * 
	 * @return the current {@link FixedIntakeDirection} which could be null.
	 */
	public FixedIntakeDirection getFixedIntakeDirection() {
		return this.fixedIntakeDirection;
	}

	/**
	 * Sets the {@link FixedIntakeDirection}. The value will only be changed and the
	 * rotation controller configured if the value is different than the current
	 * setting. If set to null the controller is reset and stick rotation turned on.
	 * If set to a fixed direction, the controller is reset, enabled and stick
	 * rotation turned off.
	 * 
	 * @param fid
	 *            the desired {@link FixedIntakeDirection}.
	 */
	public void setFixedIntakeDirection(FixedIntakeDirection fid) {
		if (this.fixedIntakeDirection != fid) {
			this.fixedIntakeDirection = fid;
			this.rotationController.reset();
			if (this.fixedIntakeDirection != null) {
				this.stickRotationDetected = false;
				this.rotationController.enable();
			} else {
				this.stickRotationDetected = true;
			}
		}
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
	 * @param manualStickRotation
	 *            If non-zero, manual rotation mode will be entered and the fixed
	 *            rotation control turned off.
	 */
	public void cartesianDrive(double ySpeed, double xSpeed, double manualStickRotation) {
		if (manualStickRotation != 0.0) {
			this.setFixedIntakeDirection(null);
			this.stickRotationDetected = true;
		}
		double rotation = 0.0;
		if (this.stickRotationDetected) {
			rotation = manualStickRotation;
		} else if (this.fixedIntakeDirection != null) {
			rotation = this.lastPIDOutput;
		}
		this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation,
				this.driveMode == DriveMode.FIELD_RELATIVE ? this.getGyroYaw() : 0.0);
	}

	/**
	 * @return the current gyro yaw.
	 */
	public double getGyroYaw() {
		return this.gyro.getYaw();
	}

	/**
	 * @return the configured initial drive mode for tele-op.
	 */
	public DriveMode getInitialTeleDriveMode() {
		return props.initialTeleDriveMode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pidWrite(double output) {
		this.lastPIDOutput = output;
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
