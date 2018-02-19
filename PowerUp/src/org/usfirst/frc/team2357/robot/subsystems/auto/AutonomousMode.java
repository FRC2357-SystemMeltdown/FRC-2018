package org.usfirst.frc.team2357.robot.subsystems.auto;

import java.util.function.Supplier;

import org.usfirst.frc.team2357.robot.subsystems.auto.commands.DriveAutoLine;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.StartFarLeft;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.StartRightSwitch;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This enumeration defines the autonomous modes available in the system. Each
 * enum value is constructed with a supplier for its {@link Command}. This is
 * done so that only the one command that will be run needs to be constructed.
 */
enum AutonomousMode {
	/** Start anywhere and drive. */
	DriveAutoline(() -> new DriveAutoLine()),

	/** Start all the way to the left. */
	StartFarLeft(() -> new StartFarLeft()),

	/** Start across from the right switch plate. */
	StartRightSwitch(() -> new StartRightSwitch());

	private final Supplier<Command> autoCommandSupplier;
	private Command autoCommand;

	private AutonomousMode(Supplier<Command> autoCommandSupplier) {
		this.autoCommandSupplier = autoCommandSupplier;
	}

	/**
	 * Access the default {@link AutonomousMode}.
	 * 
	 * @return the default {@link AutonomousMode}.
	 */
	public static AutonomousMode getDefault() {
		return AutonomousMode.DriveAutoline;
	}

	/**
	 * Starts the selected command. The command is only started if this method has
	 * not been called since startup or stop has been called since the last start.
	 */
	public synchronized void start() {
		if (this.autoCommand == null) {
			this.autoCommand = this.autoCommandSupplier.get();
			this.autoCommand.start();
		}
	}

	/**
	 * Starts the selected command. The command is only started if this method has
	 * not been called since startup or stop has been called since the last start.
	 */
	public synchronized void stop() {
		if (this.autoCommand != null) {
			this.autoCommand.cancel();
			this.autoCommand = null;
		}
	}
}
