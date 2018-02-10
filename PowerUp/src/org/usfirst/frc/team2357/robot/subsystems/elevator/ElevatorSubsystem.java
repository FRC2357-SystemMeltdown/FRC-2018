package org.usfirst.frc.team2357.robot.subsystems.elevator;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link ElevatorSubsystem} is primarily controlled using positional pid on
 * board a Talon SRX motor controller. The {@link Floors} enum values can be fed
 * to the {@link #gotoFloor(Floors)} method to set a new target. Small
 * adjustment values can be added via the {@link #tweakTarget(int)} method.
 * 
 * <p>
 * If during a match it is determined that the targeting is off, the manual mode
 * can be used to manually drive the elevator to the bottom for reset at which
 * time a switch out of manual mode would be done.
 * </p>
 */
public class ElevatorSubsystem extends Subsystem {

	/**
	 * Target elevator stops with clicks from bottom.
	 */
	public enum Floors {
		/**
		 * Ground floor (cubes for sale).
		 */
		FLOOR(0),

		/**
		 * Please keep your cube inside the ride.
		 */
		CARRY(100),

		/**
		 * Eject your cube for a switch.
		 */
		SCORE_SWITCH(200),

		/**
		 * Cube pile on the scale!!!
		 */
		SCORE_SCALE_WE_OWN(300),

		/**
		 * Excuse me while I take ownership of that scale.
		 */
		SCORE_SCALE_AT_NEUTRAL(400),

		/**
		 * All your scale are belong to us.
		 */
		SCORE_SCALE_THEY_OWN(500),

		/**
		 * Get that hook just above that rung.
		 */
		CLIMB_INITIATION(450);

		private final int clicksFromGround;

		private Floors(int clicksFromGround) {
			this.clicksFromGround = clicksFromGround;
		}

		/**
		 * Return the number of clicks from the ground for this floor.
		 * 
		 * @return return the number of clicks from the ground for this floor.
		 */
		public int getClicksFromGround() {
			return this.clicksFromGround;
		}
	}

	private final WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR);
	// TODO do we want two limit switches here or direct to the Talon?

	private final ElevatorProperties props = new ElevatorProperties();

	/*
	 * Initialize assuming the pre-match we set it to the carry position. TODO
	 * decide if this is what we want or FLOOR.
	 */
	private int lastTargetClicksSentToTalon = Floors.CARRY.getClicksFromGround();
	private boolean manualOverride = false;

	/**
	 * Registers with the configuration subsystem (drive properties set at this
	 * time), sets up the turn controller (this subsystem acting as a PID source and
	 * output). It also initializes the drive controllers.
	 */
	public ElevatorSubsystem() {
		super();
		Robot.getInstance().getConfigurationSubsystem().addConsumer(this.props);

		configMotorForPosition(this.elevatorMotor, this.lastTargetClicksSentToTalon);
	}

	private void configMotorForPosition(WPI_TalonSRX talon, int atPosition) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talon.configAllowableClosedloopError(0, this.props.positionElevatorPIDokError, 0);
		talon.config_kP(0, this.props.positionElevatorPIDp, 0);
		talon.set(ControlMode.Position, atPosition);
		talon.getSensorCollection().setQuadraturePosition(atPosition, 0);
	}

	private void configMotorForManual(WPI_TalonSRX talon) {
		talon.set(ControlMode.PercentOutput, 0.0);
	}

	/**
	 * {@inheritDoc}
	 */
	public void initDefaultCommand() {
		// So far, no need for a default command.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Check with the talon to see if either limit switch has been hit. If so, reset
	 * to match.
	 * </p>
	 */
	@Override
	public void periodic() {
		super.periodic();
		// TODO read limit states
		// if floor set target and pos to 0 on talon and our last sent to 0
		// similarly for the top with the top click value.
	}

	/**
	 * Used to enter or exit manual mode.
	 * 
	 * @param manual
	 *            true for manual mode and false for positional pid control.
	 */
	private void setManualMode(boolean manual) {
		if (manual) {
			configMotorForManual(elevatorMotor);
		} else {
			configMotorForPosition(elevatorMotor, elevatorMotor.getSensorCollection().getQuadraturePosition());
		}
		this.manualOverride = manual;
	}

	/**
	 * Used to stop in manual mode. Usually from command end methods.
	 */
	public void stop() {
		this.elevatorMotor.stopMotor();
	}

	/**
	 * @return the true if manual override.
	 */
	public boolean isManualOverride() {
		return this.manualOverride;
	}

	/**
	 * If in manual, changes to positional pid control and then sets the target to
	 * the desired floor position.
	 * 
	 * @param floor
	 *            the floor to which we want to move the elevator.
	 */
	public void gotoFloor(Floors floor) {
		if (isManualOverride()) {
			this.setManualMode(false);
		}
		this.lastTargetClicksSentToTalon = floor.getClicksFromGround();
		elevatorMotor.set(ControlMode.Position, this.lastTargetClicksSentToTalon);
	}

	/**
	 * Adjust position by a small negative or positive amount.
	 * 
	 * @param tweak
	 *            a tweak to the positional mode target.
	 */
	public void tweakTarget(final int tweak) {
		this.lastTargetClicksSentToTalon = this.lastTargetClicksSentToTalon + tweak;
		this.elevatorMotor.set(ControlMode.Position, this.lastTargetClicksSentToTalon);
	}

	/**
	 * If in auto, changes to manual control and then sets elevator speed.
	 * 
	 * @param speed
	 *            the speed (-1.0 to 1.0) at which to move.
	 */
	public void manualMovement(double speed) {
		if (!isManualOverride()) {
			this.setManualMode(true);
		}
		elevatorMotor.set(ControlMode.PercentOutput, speed);
	}

	/**
	 * An instance of this class is used by the {@link ElevatorSubsystem} to manage
	 * the elevator properties using the {@link ConfigurationSubsystem}.
	 */
	public class ElevatorProperties implements ConfigurationPropertiesConsumer {
		/**
		 * The elevator position pid control p value. TODO add others as needed.
		 */
		public static final String POSITION_ELEVATOR_PID_P_KEY = "elevator.position.pid.p";
		public static final double POSITION_ELEVATOR_PID_P_DEFAULT = 0.5;
		private double positionElevatorPIDp = POSITION_ELEVATOR_PID_P_DEFAULT;

		/**
		 * The elevator position acceptable error for on target.
		 */
		public static final String POSITION_ELEVATOR_PID_OK_ERROR_KEY = "elevator.position.pid.ok.error";
		public static final int POSITION_ELEVATOR_PID_OK_ERROR_DEFAULT = 10;
		private int positionElevatorPIDokError = POSITION_ELEVATOR_PID_OK_ERROR_DEFAULT;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void reset(ConfigurationSubsystem config) {
			this.positionElevatorPIDp = ConfigurationUtilities.getProperty(config, POSITION_ELEVATOR_PID_P_KEY,
					POSITION_ELEVATOR_PID_P_DEFAULT);

			this.positionElevatorPIDokError = ConfigurationUtilities.getProperty(config,
					POSITION_ELEVATOR_PID_OK_ERROR_KEY, POSITION_ELEVATOR_PID_OK_ERROR_DEFAULT);
		}
	}
}
