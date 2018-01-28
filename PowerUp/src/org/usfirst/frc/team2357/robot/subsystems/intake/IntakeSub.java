package org.usfirst.frc.team2357.robot.subsystems.intake;

import org.usfirst.frc.team2357.robot.subsystems.intake.commands.IntakeCommand;
import org.usfirst.frc.team2357.robot.subsystems.intake.states.IntakeStates;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSub extends Subsystem {
	
	private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0,1);
	private IntakeStates state = IntakeStates.closed;
	
	public IntakeSub(){
		
	}

    public void initDefaultCommand() {
        setDefaultCommand(new IntakeCommand());
    }

	public IntakeStates getState() {
		return state;
	}

	public void setState(IntakeStates state) {
		this.state = state;
	}

	public DoubleSolenoid getIntakeSolenoid() {
		return intakeSolenoid;
	}
}

