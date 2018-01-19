package org.usfirst.frc.team2357.robot.subsystems.auto;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem is used to select an {@link AutonomousMode} and a sprint
 * option. Also, the {@link Robot} uses this subsystem to run and stop the
 * {@link AutonomousMode}.
 */
public class AutonomousSubsystem extends Subsystem {
	private final AutonomousChooser autonomousChooser = new AutonomousModeDashboardChooser();
	private final SendableChooser<Boolean> autoSprint = new SendableChooser<>();
	private AutonomousMode startedMode;

	/**
	 * Initializes the subsystem.
	 */
	public AutonomousSubsystem() {
		super();
		this.autoSprint.addDefault("No sprint", Boolean.FALSE);
		this.autoSprint.addObject("Sprint", Boolean.TRUE);
		SmartDashboard.putData("Sprint", this.autoSprint);
	}

	/**
	 * Should be called once from {@link Robot#autonomousInit()}.
	 */
	public void autonomousInit() {
		// For safety during testing.
		stop();

		this.startedMode = this.autonomousChooser.getAutonomousMode();
		this.startedMode.getAutonomousCommand().start();
	}

	/**
	 * Should be called once from {@link Robot#teleopInit()}.
	 */
	public void teleopInit() {
		stop();
	}

	/**
	 * Should be called once from {@link Robot#disabledInit()}.
	 */
	public void disabledInit() {
		stop();
	}

	/**
	 * Stops any started {@link AutonomousMode} and sets the started mode to
	 * null.
	 */
	public void stop() {
		if (this.startedMode != null) {
			this.startedMode.getAutonomousCommand().cancel();
			this.startedMode = null;
		}
	}

	/**
	 * Returns true if the chosen autonomous mode is free to sprint toward the
	 * feeder station. Note that the default value is false.
	 * 
	 * @return true if free to sprint and false otherwise.
	 */
	public boolean isAutoSprint() {
		return this.autoSprint.getSelected();
	}

	/**
	 * There is no default command for this subsystem.
	 */
	public void initDefaultCommand() {
	}
}
