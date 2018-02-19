package org.usfirst.frc.team2357.robot.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DPadLeftTrigger extends Trigger {
	private final XboxController controller;

	public DPadLeftTrigger(XboxController controller) {
		this.controller = controller;
	}

	public boolean get() {
		return 270 == this.controller.getPOV();
	}
}
