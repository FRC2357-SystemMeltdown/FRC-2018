package org.usfirst.frc.team2357.robot.subsystems.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Reads robot configuration information from a properties file on board the
 * robot and makes it available to the rest of the robot code.
 */
public class ConfigurationSubsystem extends Subsystem {
	public static final String PROPERTIES_FILE = "/home/lvuser.team2357Properties.ini";

	private final Properties properties = new Properties();
	private final Queue<ConfigurationPropertiesConsumer> consumers = new ConcurrentLinkedQueue<>();

	/**
	 * Initializes configuration from the properties file.
	 */
	public ConfigurationSubsystem() {
		initProperties();
	}

	/**
	 * Use with care as this will force the configuration information to be
	 * re-read and the robot configuration will change. This should typically be
	 * attached to a dash board or controller interaction that takes very
	 * deliberate action to trigger. For example, one button on both controllers
	 * being pressed together.
	 * 
	 * <p>
	 * This method will re-initialize the configuration properties. All the
	 * registered {@link ConfigurationPropertiesConsumer} instances will then be
	 * reset.
	 * </p>
	 */
	public synchronized void reset() {
		initProperties();
		consumers.forEach(consumer -> consumer.reset(this));
	}

	/**
	 * Subsystems and other consumers of configuration properties must add their
	 * {@link ConfigurationPropertiesConsumer} implementation via this method.
	 * The consumer cannot be removed.
	 * 
	 * <p>
	 * The {@link ConfigurationPropertiesConsumer#reset()} method is called on
	 * the consumer after it has been registered to give the consumer the
	 * opportunity to initialize itself.
	 * </p>
	 * 
	 * @param consumer
	 *            the {@link ConfigurationPropertiesConsumer} to be added.
	 */
	public void addConsumer(ConfigurationPropertiesConsumer consumer) {
		this.consumers.add(consumer);
		consumer.reset(this);
	}

	/**
	 * Searches for the property with the specified key in the configuration
	 * properties. The method returns the default value argument if the property
	 * is not found.
	 *
	 * @param key
	 *            the property key to look up.
	 * @param defaultValue
	 *            a default value which is returned if the key is not found.
	 *
	 * @return the value in this property list with the specified key value.
	 */
	public String getProperty(String key, String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

	/**
	 * There is no default command for the configuration subsystem.
	 */
	public void initDefaultCommand() {
	}

	/**
	 * Reads the on board properties file and sets values from it. All values
	 * MUST have defaults in case the file is not present or the value is not
	 * set in the file.
	 */
	private void initProperties() {
		this.properties.clear();
		File propFile = new File(PROPERTIES_FILE);
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(propFile);
			this.properties.load(inStream);
		} catch (FileNotFoundException ffe) {
			// Okay to not have a file
			Logger logger = Robot.getInstance().getLogger();
			logger.log(Level.INFO, "No config file found.");
		} catch (IOException e) {
			Logger logger = Robot.getInstance().getLogger();
			logger.log(Level.SEVERE, "IOException encountered while initializing configuration properties.", e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					Logger logger = Robot.getInstance().getLogger();
					logger.log(Level.WARNING, "IOException encountered while closing properties file input stream.");
				}
			}
		}
	}
}
