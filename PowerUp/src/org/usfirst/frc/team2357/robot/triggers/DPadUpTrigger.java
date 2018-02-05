package org.usfirst.frc.team2357.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;

import org.usfirst.frc.team2357.robot.Robot;

/**
 *
 */
public class DPadUpTrigger extends Trigger {

    public boolean get() {
        return 0 == Robot.getInstance().getOI().getDriveController().getPOV(); 
    }
}
