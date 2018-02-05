package org.usfirst.frc.team2357.robot.triggers;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class DPadDownTrigger extends Trigger {

    public boolean get() {
        return 180 == Robot.getInstance().getOI().getDriveController().getPOV(); 
    }
}
