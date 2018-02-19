package org.usfirst.frc.team2357.robot.subsystems.drive;

import java.util.LinkedList;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.OperatorDriveCommand;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
		 * Cartesian movement is relative to the right side of the robot with the X and Y
		 * components derived from the position of the movement stick.
		 */
		ROBOT_RELATIVE_90,
		
		/**
		 * Cartesian movement is relative to the back side of the robot with the X and Y
		 * components derived from the position of the movement stick.
		 */
		ROBOT_RELATIVE_180,
		
		/**
		 * Cartesian movement is relative to the left side of the robot with the X and Y
		 * components derived from the position of the movement stick.
		 */
		ROBOT_RELATIVE_270,

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
	private final LinkedList<WPI_TalonSRX> motors = new LinkedList<WPI_TalonSRX>();
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
		this.motors.add(frontLeftMotor);
		this.motors.add(frontRightMotor);
		this.motors.add(backLeftMotor);
		this.motors.add(backRightMotor);

		rotationController = new PIDController(props.rotatePIDp, props.rotatePIDi, props.rotatePIDd, 0.0, this.gyro,
				this, 0.01);
		rotationController.setInputRange(-180.0, 180.0);
		rotationController.setOutputRange(-0.7, 0.7);
		rotationController.setAbsoluteTolerance(props.rotatePIDTolerance);
		rotationController.setContinuous(true);

		configMotor(frontLeftMotor);
		configMotor(frontRightMotor);
		configMotor(backLeftMotor);
		configMotor(backRightMotor);
		
		frontLeftMotor.setInverted(false);
		frontRightMotor.setInverted(false);
		backLeftMotor.setInverted(false);
		backRightMotor.setInverted(false);
		
		frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		backLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		backRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	}

	/**
	 * Used to configure each Talon. Not much here now, but may add more.
	 * 
	 * @param talon
	 *            the Talon being configured.
	 */
	private void configMotor(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
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
	 *            The robot's speed along the Y axis [-1.0..1.0].
	 * @param xSpeed
	 *            The robot's speed along the X axis [-1.0..1.0].
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
		switch (this.driveMode) {
		case FIELD_RELATIVE:
			this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation, -this.getGyroYaw());
			break;
		case ROBOT_RELATIVE:
			this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation, 0.0);
			break;
		case ROBOT_RELATIVE_90:
			this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation, 90.0);
			break;
		case ROBOT_RELATIVE_180:
			this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation, 180.0);
			break;
		case ROBOT_RELATIVE_270:
			this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation, -90.0);
			break;
		}
		for(int i = 0; i < motors.size(); i++){
			WPI_TalonSRX tempMotor = motors.get(i);
			if(Math.abs(tempMotor.get()+(tempMotor.get()-(tempMotor.getSelectedSensorVelocity(0)/1000))) < 1){
				tempMotor.set(tempMotor.get()+(tempMotor.get()-(tempMotor.getSelectedSensorVelocity(0)/1000)));
			}else if(tempMotor.get() > 0){
				tempMotor.set(1.0);
			}else if(tempMotor.get() < 0){
				tempMotor.set(-1.0);
			}
			
		}
		
		//this.mecanumDrive.driveCartesian(ySpeed, xSpeed, rotation,
				//this.driveMode == DriveMode.FIELD_RELATIVE ? -this.getGyroYaw() : 0.0);
	}

	/**
	 * @return the current gyro yaw.
	 */
	public double getGyroYaw() {
		double yaw = this.gyro.getYaw();
		yaw += 90.0;
		if (yaw > 180.0) {
			yaw -= 360.0;
		}
		return yaw;
	}
	
	public AHRS getGyro(){
		return gyro;
	}

	/**
	 * @return the configured initial drive mode for tele-op.
	 */
	public DriveMode getInitialTeleDriveMode() {
		return props.initialTeleDriveMode;
	}

	public WPI_TalonSRX getFrontLeftMotor() {
		return frontLeftMotor;
	}

	public WPI_TalonSRX getFrontRightMotor() {
		return frontRightMotor;
	}

	public WPI_TalonSRX getBackLeftMotor() {
		return backLeftMotor;
	}

	public WPI_TalonSRX getBackRightMotor() {
		return backRightMotor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pidWrite(double output) {
		this.lastPIDOutput = output;
	}

	/* START DRIVE FIXED DISTANCE FUNCTIONS */

	/**
	 * Do not use the Talon on-board pid but do reset. We need to merge sensor on
	 * the Rio.
	 * 
	 * @param inches
	 *            the number of inches to move forward (negative for backward).
	 * 
	 * @return the number of encoder clicks to move. Feed this back to
	 *         {@link #isPositionOnTarget(double)}.
	 */
	public int startMoveInches(double inches) {
		enableMoveInches();
		return (int) ((inches / this.props.effectiveWheelCircumference) * this.props.effectiveEncoderRevPerWheelRev
				* this.props.encoderClicksPerRev);
	}

	/**
	 * Sets up all the Talons for moving inches.
	 */
	private void enableMoveInches() {
		enableMoveInches(this.frontLeftMotor);
		enableMoveInches(this.frontRightMotor);
		enableMoveInches(this.backLeftMotor);
		enableMoveInches(this.backRightMotor);
	}

	/**
	 * For now just set counts to 0.
	 * 
	 * @param motor
	 *            the Talon being set.
	 */
	private void enableMoveInches(WPI_TalonSRX motor) {
		motor.getSensorCollection().setQuadraturePosition(0, 0);
	}

	/**
	 * Returns true if we have gone the number of clicks. This is the same as
	 * checking that the result of {@link #getTargetObtainedPercentage(int)} is
	 * greater than or equal to 1.0.
	 * 
	 * @param targetClicks
	 *            the calculated clicks from {@link #startMoveInches(double)}.
	 * 
	 * @return true if the robot has moved the number of clicks and false otherwise.
	 */
	public boolean isPositionOnTarget(int targetClicks) {
		return getTargetObtainedPercentage(targetClicks) >= 1.0;
	}

	/**
	 * Returns a positive value indicating the percentage (1.0 is 100%) of the
	 * target clicks obtained. Only encoders that are within 80% of the maximum
	 * encoder click value are considered.
	 * 
	 * @param targetClicks
	 *            the target click value.
	 * @return the percentage of click obtainment.
	 */
	public double getTargetObtainedPercentage(int targetClicks) {
		int[] clicks = new int[4];
		int max = 0;
		clicks[0] = this.frontLeftMotor.getSensorCollection().getQuadraturePosition();
		max = clicks[0];
		clicks[1] = this.frontRightMotor.getSensorCollection().getQuadraturePosition();
		max = Math.max(max, clicks[1]);
		clicks[2] = this.backLeftMotor.getSensorCollection().getQuadraturePosition();
		max = Math.max(max, clicks[2]);
		clicks[3] = this.backRightMotor.getSensorCollection().getQuadraturePosition();
		max = Math.max(max, clicks[3]);
		int total = 0;
		int encodersInTotal = 0;
		for (int i = 0; i < clicks.length; i++) {
			if ((((double)clicks[i]) / ((double)max)) > 0.8) {
				total += clicks[i];
				encodersInTotal++;
			}
		}
		return (double)total / (double)encodersInTotal / (double)targetClicks;
	}

	/**
	 * Stops the inches movement.
	 */
	public void stopMoveInches() {
		this.stop();
	}

	/* END DRIVE FIXED DISTANCE FUNCTIONS */

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
		 * This is the effective circumference of the wheels. It is used as part of the
		 * encoder based position control. The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String EFFECTIVE_WHEEL_CIRCUMFERENCE_KEY = "drive.effective.wheel.circumference";
		public static final double EFFECTIVE_WHEEL_CIRCUMFERENCE_DEFAULT = 18.85;
		private double effectiveWheelCircumference = EFFECTIVE_WHEEL_CIRCUMFERENCE_DEFAULT;

		/**
		 * This is the effective number of encoder revolutions per wheel revolution. It
		 * is based on gear ratios between the wheels and the encode. It is used as part
		 * of the encoder based position control. The value must be parseable into a
		 * double via {@link Double#parseDouble(String)} or the default value will be
		 * used.
		 */
		public static final String EFFECTIVE_ENCODER_REV_PER_WHEEL_REV_KEY = "drive.effective.encoder.rev.per.wheel.rev";
		public static final double EFFECTIVE_ENCODER_REV_PER_WHEEL_REV_DEFAULT = 1.0;
		private double effectiveEncoderRevPerWheelRev = EFFECTIVE_ENCODER_REV_PER_WHEEL_REV_DEFAULT;

		public static final String ENCODER_CLICKS_PER_REV_KEY = "drive.encoder.clicks.per.rev";
		public static final double ENCODER_CLICKS_PER_REV_DEFAULT = 256.0;
		private double encoderClicksPerRev = ENCODER_CLICKS_PER_REV_DEFAULT;

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

			this.effectiveWheelCircumference = ConfigurationUtilities.getProperty(config,
					EFFECTIVE_WHEEL_CIRCUMFERENCE_KEY, EFFECTIVE_WHEEL_CIRCUMFERENCE_DEFAULT);
			this.effectiveEncoderRevPerWheelRev = ConfigurationUtilities.getProperty(config,
					EFFECTIVE_ENCODER_REV_PER_WHEEL_REV_KEY, EFFECTIVE_ENCODER_REV_PER_WHEEL_REV_DEFAULT);
			this.encoderClicksPerRev = ConfigurationUtilities.getProperty(config, ENCODER_CLICKS_PER_REV_KEY,
					ENCODER_CLICKS_PER_REV_DEFAULT);
		}
	}
}
