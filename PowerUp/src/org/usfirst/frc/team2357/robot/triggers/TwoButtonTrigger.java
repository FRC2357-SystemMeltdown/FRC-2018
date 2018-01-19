package org.usfirst.frc.team2357.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * Used to make sure both drivers agree to an action. To accomplish this, the
 * two buttons passed in to the constructor must be one from each controller.
 */
public class TwoButtonTrigger extends Trigger {
	private final Button button1;
	private final Button button2;

	/**
	 * Constructs the trigger which will check the two specified buttons.
	 * 
	 * @param button1
	 *            the first button to check.
	 * @param button2
	 *            the second button that should be on a different controller
	 *            than button1.
	 */
	public TwoButtonTrigger(Button button1, Button button2) {
		this.button1 = button1;
		this.button2 = button2;
	}

	/**
	 * Returns true if both drivers have their button pressed.
	 */
	public boolean get() {
		return this.button1.get() && this.button2.get();
	}
}
