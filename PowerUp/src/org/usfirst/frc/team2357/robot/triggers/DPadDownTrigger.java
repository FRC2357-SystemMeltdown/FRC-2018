package org.usfirst.frc.team2357.robot.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DPadDownTrigger extends Trigger {
	private final XboxController controller;

	public DPadDownTrigger(XboxController controller) {
		this.controller = controller;
	}

	public boolean get() {
		return 180 == this.controller.getPOV();
	}
}
