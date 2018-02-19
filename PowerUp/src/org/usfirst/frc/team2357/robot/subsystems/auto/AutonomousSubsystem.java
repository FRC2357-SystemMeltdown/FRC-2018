package org.usfirst.frc.team2357.robot.subsystems.auto;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
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
	private AutonomousMode startedMode;
	private PlatformSide switchSide = PlatformSide.UNKNOWN;
	private PlatformSide scaleSide = PlatformSide.UNKNOWN;
	private final SendableChooser<TargetPreference> targetPrefernceChooser;
	private TargetPreference targetPreference = null;
	private double autoStartWaitTime = 0.0;
	private double autoPostScoreFirstCubeWaitTime = 0.0;
	private final SendableChooser<CubeTwoOptions> cubeTwoOptionChooser;
	private CubeTwoOptions cube2Option = CubeTwoOptions.NO_CUBE_TWO;

	/**
	 * Initializes the subsystem.
	 */
	public AutonomousSubsystem() {
		super();
		this.targetPrefernceChooser = new SendableChooser<>();
		TargetPreference[] positions = TargetPreference.values();
		for (TargetPreference position : positions) {
			if (position == TargetPreference.PREFER_SWITCH) {
				this.targetPrefernceChooser.addDefault(position.name(), position);
			} else {
				this.targetPrefernceChooser.addObject(position.name(), position);
			}
		}
		SmartDashboard.putData("Target Preference", this.targetPrefernceChooser);

		this.cubeTwoOptionChooser = new SendableChooser<>();
		this.cubeTwoOptionChooser.addDefault("No cube 2", CubeTwoOptions.NO_CUBE_TWO);
		this.cubeTwoOptionChooser.addObject("Cube 2 on other target", CubeTwoOptions.OTHER);
		this.cubeTwoOptionChooser.addObject("Cube 2 on same target", CubeTwoOptions.SAME);
	}

	/**
	 * Should be called once from {@link Robot#autonomousInit()}.
	 */
	public void autonomousInit() {
		// For safety during testing.
		stop();

		this.autoStartWaitTime = SmartDashboard.getNumber("DB/Slider 0", 0.0);
		this.autoPostScoreFirstCubeWaitTime = SmartDashboard.getNumber("DB/Slider 1", 0.0);
		this.targetPreference = this.targetPrefernceChooser.getSelected();
		this.cube2Option = this.cubeTwoOptionChooser.getSelected();
		processGameData();

		if (getSwitchSide() == PlatformSide.UNKNOWN) {
			// Never got game data just drive.
			this.startedMode = AutonomousMode.DriveAutoline;
		} else {
			this.startedMode = this.autonomousChooser.getAutonomousMode();
		}
		this.startedMode.start();
	}

	/**
	 * Reads the game specific data and processes it into the platform sides.
	 */
	private void processGameData() {
		int retries = 100;
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		// Should never loop in our code but reports from week 0 events
		// indicate that the data may be slightly delayed.
		while (((gameData == null) || (gameData.length() < 2)) && retries > 0) {
			retries--;
			try {
				Thread.sleep(5);
			} catch (InterruptedException ie) {
				// Just ignore the interrupted exception
			}
			gameData = DriverStation.getInstance().getGameSpecificMessage();
		}
		if ((gameData != null) && (gameData.length() > 1)) {
			this.switchSide = gameData.charAt(0) == 'R' ? PlatformSide.RIGHT : PlatformSide.LEFT;
			this.scaleSide = gameData.charAt(1) == 'R' ? PlatformSide.RIGHT : PlatformSide.LEFT;
		}
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
	 * Stops any started {@link AutonomousMode} and sets the started mode to null.
	 */
	public void stop() {
		if (this.startedMode != null) {
			this.startedMode.stop();
			this.startedMode = null;
		}
	}

	/**
	 * There is no default command for this subsystem.
	 */
	public void initDefaultCommand() {
	}

	/**
	 * @return the {@link PlatformSide} for our switch platform from the point of
	 *         view of the drive team.
	 */
	public PlatformSide getSwitchSide() {
		return this.switchSide;
	}

	/**
	 * @return the {@link PlatformSide} for our scale platform from the point of
	 *         view of the drive team.
	 */
	public PlatformSide getScaleSide() {
		return this.scaleSide;
	}

	/**
	 * @return the {@link TargetPreference} for the robot this match.
	 */
	public TargetPreference getTargetPreference() {
		return this.targetPreference;
	}

	/**
	 * @return the time to wait in seconds before moving in autonomous.
	 */
	public double getAutoStartWaitTime() {
		return this.autoStartWaitTime;
	}

	/**
	 * @return the time to wait in seconds after scoring the first cube in
	 *         autonomous.
	 */
	public double getAutoPostScoreFirstCubeWaitTime() {
		return autoPostScoreFirstCubeWaitTime;
	}

	/**
	 * @return the driver station choosen option for cube 2.
	 */
	public CubeTwoOptions getCubeTwoOption() {
		return this.cube2Option;
	}
}
