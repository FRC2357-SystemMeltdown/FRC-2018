package org.usfirst.frc.team2357.robot.subsystems.configuration;

/**
 * Implementations of this interface can define, interpret and provide default
 * values for configuration properties required by a subsystem or other type of
 * classes that make up our robot implementation.
 */
public interface ConfigurationPropertiesConsumer {
	/**
	 * Called on a {@link ConfigurationSubsystem} registered consumer after the
	 * {@link ConfigurationSubsystem#reset()} has been called. Consumers should
	 * respond by resetting their own properties state using the
	 * {@link ConfigurationSubsystem}.
	 * 
	 * @param config
	 *            the {@link ConfigurationSubsystem} that was reset (passed in
	 *            for convenience).
	 */
	public void reset(ConfigurationSubsystem config);
}
