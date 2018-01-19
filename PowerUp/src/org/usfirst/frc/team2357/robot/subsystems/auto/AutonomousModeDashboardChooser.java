package org.usfirst.frc.team2357.robot.subsystems.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Defines the available commands via a {@link SendableChooser} on the dash
 * board. Selection is from the dash board. Also defines the default command to
 * use if the dash board chooser fails.
 */
class AutonomousModeDashboardChooser implements AutonomousChooser {
	private SendableChooser<AutonomousMode> chooser;

	/**
	 * Adds the autonomous modes to the dash board chooser. Note that the
	 * chooser is only created if this method of autonomous selection is created
	 * at runtime.
	 */
	public AutonomousModeDashboardChooser() {
		this.chooser = new SendableChooser<>();
		AutonomousMode[] autos = AutonomousMode.values();
		for (AutonomousMode autonomousMode : autos) {
			if (autonomousMode == AutonomousMode.getDefault()) {
				this.chooser.addDefault(autonomousMode.name(), autonomousMode);
			} else {
				this.chooser.addObject(autonomousMode.name(), autonomousMode);
			}
		}
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AutonomousMode getAutonomousMode() {
		AutonomousMode automode = chooser.getSelected();
		return automode == null ? AutonomousMode.getDefault() : automode;
	}
}