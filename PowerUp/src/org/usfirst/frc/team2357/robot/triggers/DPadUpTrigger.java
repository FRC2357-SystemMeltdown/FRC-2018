package org.usfirst.frc.team2357.robot.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DPadUpTrigger extends Trigger {
	private final XboxController controller;

	public DPadUpTrigger(XboxController controller) {
		this.controller = controller;
	}

	public boolean get() {
		return 0 == this.controller.getPOV();
	}
}
