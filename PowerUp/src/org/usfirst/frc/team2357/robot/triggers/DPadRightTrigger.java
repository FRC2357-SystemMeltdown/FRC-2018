package org.usfirst.frc.team2357.robot.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DPadRightTrigger extends Trigger {
	private final XboxController controller;

	public DPadRightTrigger(XboxController controller) {
		this.controller = controller;
	}

	public boolean get() {
		return 90 == this.controller.getPOV();
	}
}
