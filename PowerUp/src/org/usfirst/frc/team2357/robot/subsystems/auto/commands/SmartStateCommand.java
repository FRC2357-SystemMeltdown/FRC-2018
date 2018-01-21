package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.command.NextStateProvider;
import org.usfirst.frc.team2357.command.StateCommand;
import org.usfirst.frc.team2357.robot.subsystems.auto.AutonomousSubsystem;

/**
 * This year all the {@link StateCommand}s will choose the next state in
 * consultation with the {@link AutonomousSubsystem}. The subsystem read the
 * dashboard and the field data to calculate the target and route.
 */
public interface SmartStateCommand extends StateCommand, NextStateProvider {

}
