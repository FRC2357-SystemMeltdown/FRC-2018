package org.usfirst.frc.team2357.robot.subsystems.auto;

import java.util.function.Supplier;

import org.usfirst.frc.team2357.robot.subsystems.auto.commands.DriveAutoLine;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This enumeration defines the autonomous modes available in the system. Each
 * enum value is constructed with a supplier for its {@link Command}. This is
 * done so that only the one command that will be run needs to be constructed.
 */
enum AutonomousMode {
	DriveAutoline(() -> new DriveAutoLine());

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
	 * Returns the autonomous {@link Command} for the reference value of this
	 * enumeration.
	 * 
	 * @return the autonomous {@link Command} for one enumeration value.
	 */
	public synchronized Command getAutonomousCommand() {
		if (this.autoCommand == null) {
			this.autoCommand = this.autoCommandSupplier.get();
		}
		return this.autoCommand;
	}
}
