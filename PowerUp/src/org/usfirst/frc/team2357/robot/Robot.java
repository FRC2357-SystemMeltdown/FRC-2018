package org.usfirst.frc.team2357.robot;

import java.util.logging.Logger;

import org.usfirst.frc.team2357.robot.subsystems.auto.AutonomousSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem.DriveMode;
import org.usfirst.frc.team2357.robot.subsystems.elevator.ElevatorSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * <p>
 * Do not add any static class fields to this class other than
 * {@link Robot#INSTANCE}. This will help us avoid some difficult to diagnose
 * wpilib initialization issues. Also, leave initialization of instance fields
 * to the constructor. Where initialization order will be more explicit and
 * therefore more understandable for all our programmers.
 * </p>
 * 
 * <p>
 * The subsystem getter methods throw an exception if the subsystem is null
 * rather than lazily initialize the subsystem so that subsystem initialization
 * order and be explicitly controlled in {@link #robotInit()}.
 * </p>
 */
public class Robot extends IterativeRobot {

	/**
	 * This is where we hold the one and only instance of this class. It is set in
	 * {@link #robotInit()} and read in {@link #getInstance()}. This field should
	 * not be referenced anywhere else.
	 */
	private static Robot INSTANCE;

	/**
	 * The subsystem that manages robot configuration.
	 */
	private ConfigurationSubsystem configurationSubsystem;

	/**
	 * The subsystem that manages autonomous selection and execution.
	 */
	private AutonomousSubsystem autonomousSubsystem;

	/**
	 * Baby, I'll let you drive my robot...
	 */
	private DriveSubsystem driveSubsystem;

	/**
	 * Now servicing 7 floors.
	 */
	private ElevatorSubsystem elevatorSubsystem;

	/**
	 * This object will define the interactions used by the drivers to control the
	 * robot.
	 */
	private OI oi;

	/**
	 * The logger used for all logging in our little robot program.
	 */
	private Logger logger;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		INSTANCE = this;
		this.logger = Logger.getLogger(this.getClass().getName());
		this.configurationSubsystem = new ConfigurationSubsystem();
		this.driveSubsystem = new DriveSubsystem();
		this.elevatorSubsystem = new ElevatorSubsystem();
		this.autonomousSubsystem = new AutonomousSubsystem();
		this.oi = new OI();
	}

	/**
	 * Returns the one and only instance of the {@link Robot} class.
	 * 
	 * @return the singleton {@link Robot} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public static Robot getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException("Robot.getInstance() was called before Robot.robotInit() was called.");
		}
		return INSTANCE;
	}

	/**
	 * Returns the one and only {@link Logger} instance for this {@link Robot}.
	 * 
	 * @return the {@link Logger} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public Logger getLogger() {
		if (this.logger == null) {
			throw new IllegalStateException("Robot.getLogger() was called before Robot.robotInit() was called.");
		}
		return this.logger;
	}

	/**
	 * Returns the one and only {@link OI} instance for this {@link Robot}.
	 * 
	 * @return the {@link OI} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public OI getOI() {
		if (this.oi == null) {
			throw new IllegalStateException("Robot.getOI() was called before Robot.robotInit() was called.");
		}
		return this.oi;
	}

	/**
	 * Returns the one and only {@link ConfigurationSubsystem} instance for this
	 * {@link Robot}.
	 * 
	 * @return the {@link ConfigurationSubsystem} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public ConfigurationSubsystem getConfigurationSubsystem() {
		if (this.configurationSubsystem == null) {
			throw new IllegalStateException(
					"Robot.getConfigurationSubsystem() was called before Robot.robotInit() was called.");
		}
		return this.configurationSubsystem;
	}

	/**
	 * Returns the one and only {@link AutonomousSubsystem} instance for this
	 * {@link Robot}.
	 * 
	 * @return the {@link AutonomousSubsystem} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public AutonomousSubsystem getAutonomousSubsystem() {
		if (this.autonomousSubsystem == null) {
			throw new IllegalStateException(
					"Robot.getAutonomousSubsystem() was called before Robot.robotInit() was called.");
		}
		return this.autonomousSubsystem;
	}

	/**
	 * Returns the one and only {@link DriveSubsystem} instance for this
	 * {@link Robot}.
	 * 
	 * @return the {@link DriveSubsystem} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public DriveSubsystem getDriveSubsystem() {
		if (this.driveSubsystem == null) {
			throw new IllegalStateException(
					"Robot.getDriveSubsystem() was called before Robot.robotInit() was called.");
		}
		return this.driveSubsystem;
	}

	/**
	 * Returns the one and only {@link ElevatorSubsystem} instance for this
	 * {@link Robot}.
	 * 
	 * @return the {@link ElevatorSubsystem} instance.
	 * 
	 * @throws IllegalStateException
	 *             if this method is called before {@link #robotInit()} is called.
	 */
	public ElevatorSubsystem getElevatorSubsystem() {
		if (this.elevatorSubsystem == null) {
			throw new IllegalStateException(
					"Robot.getElevatorSubsystem() was called before Robot.robotInit() was called.");
		}
		return this.elevatorSubsystem;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation informs the {@link AutonomousSubsystem} that the robot is
	 * entering the disabled state.
	 * </p>
	 */
	@Override
	public void disabledInit() {
		this.getAutonomousSubsystem().disabledInit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation runs all currently scheduled {@link Command}s.
	 * </p>
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation informs the {@link AutonomousSubsystem} that the robot is
	 * entering the autonomous state.
	 * </p>
	 */
	@Override
	public void autonomousInit() {
		this.getDriveSubsystem().setDriveMode(DriveMode.FIELD_RELATIVE);
		this.getAutonomousSubsystem().autonomousInit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation runs all currently scheduled {@link Command}s.
	 * </p>
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation informs the {@link AutonomousSubsystem} that the robot is
	 * entering the teleop state.
	 * </p>
	 */
	@Override
	public void teleopInit() {
		this.getDriveSubsystem().setDriveMode(this.getDriveSubsystem().getInitialTeleDriveMode());
		this.getAutonomousSubsystem().teleopInit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation runs all currently scheduled {@link Command}s.
	 * </p>
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void testPeriodic() {
	}
}
