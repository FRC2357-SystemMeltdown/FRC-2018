package org.usfirst.frc.team2357.robot.subsystems.configuration;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.usfirst.frc.team2357.robot.Robot;

/**
 * Utility functions for working with {@link ConfigurationSubsystem} properties.
 */
public class ConfigurationUtilities {
	public static final String NFE_MESSAGE = "NumberFormatException encountered while parsing the \"{0}\" property from value \"{1}\".";

	/**
	 * No need to ever construct an instance of {@link ConfigurationUtilities}.
	 */
	private ConfigurationUtilities() {
	}

	/**
	 * Returns a trimmed string from the properties or the default.
	 * 
	 * @param config
	 *            the {@link ConfigurationSubsystem}.
	 * @param key
	 *            the key for the property.
	 * @param defaultValue
	 *            the default value for the property.
	 * @return the trimmed string or the default if the value was not found.
	 */
	public static String getProperty(ConfigurationSubsystem config, String key, String defaultValue) {
		String value = config.getProperty(key, null);
		if (value != null) {
			value = value.trim();
		} else {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Parses a value from the properties into a double. A warning is logged if
	 * the property could not be parsed as a double.
	 * 
	 * @param config
	 *            the {@link ConfigurationSubsystem}.
	 * @param key
	 *            the key for the property.
	 * @param defaultValue
	 *            the default value for the property.
	 * @return the double value or the default if the value was not found or
	 *         could not be parsed as a double.
	 */
	public static double getProperty(ConfigurationSubsystem config, String key, double defaultValue) {
		double value = defaultValue;
		String propValue = getProperty(config, key, null);
		if (propValue != null) {
			try {
				value = Double.parseDouble(propValue);
			} catch (NumberFormatException e) {
				Logger logger = Robot.getInstance().getLogger();
				logger.log(Level.WARNING, MessageFormat.format(NFE_MESSAGE, key, propValue));
			}
		}
		return value;
	}

	/**
	 * Parses a value from the properties into an int. A warning is logged if
	 * the property could not be parsed as an int.
	 * 
	 * @param config
	 *            the {@link ConfigurationSubsystem}.
	 * @param key
	 *            the key for the property.
	 * @param defaultValue
	 *            the default value for the property.
	 * @return the int value or the default if the value was not found or could
	 *         not be parsed as an int.
	 */
	public static int getProperty(ConfigurationSubsystem config, String key, int defaultValue) {
		int value = defaultValue;
		String propValue = getProperty(config, key, null);
		if (propValue != null) {
			try {
				value = Integer.parseInt(propValue);
			} catch (NumberFormatException e) {
				Logger logger = Robot.getInstance().getLogger();
				logger.log(Level.WARNING, MessageFormat.format(NFE_MESSAGE, key, propValue));
			}
		}
		return value;
	}

	/**
	 * Parses a value from the properties into a boolean.
	 * 
	 * @param config
	 *            the {@link ConfigurationSubsystem}.
	 * @param key
	 *            the key for the property.
	 * @param defaultValue
	 *            the default value for the property.
	 * @return the boolean value or the default if the value was not found or
	 *         could not be parsed as a boolean.
	 */
	public static boolean getProperty(ConfigurationSubsystem config, String key, boolean defaultValue) {
		boolean value = defaultValue;
		String propValue = getProperty(config, key, null);
		if (propValue != null) {
			value = Boolean.parseBoolean(propValue);
		}
		return value;
	}

	/**
	 * Parses a value from the properties into an array of double. A warning is
	 * logged if any value could not be parsed as a double. The values in the
	 * property must be comma separated.
	 * 
	 * @param config
	 *            the {@link ConfigurationSubsystem}.
	 * @param key
	 *            the key for the property.
	 * @param defaultValue
	 *            the default value for the property.
	 * @param matchLength
	 *            if the property cannot be parsed into an array the same length
	 *            as the default value, the default value is returned. This is
	 *            useful for properties like RGB values that must have a fixed
	 *            number of entries to be useful.
	 * @return the double value or the default if the value was not found or
	 *         could not be parsed as an array of double.
	 */
	public static double[] getProperty(ConfigurationSubsystem config, String key, double[] defaultValue,
			boolean matchLength) {
		double[] value = defaultValue;
		String propValue = getProperty(config, key, null);
		if (propValue != null) {
			String[] splitValues = commaSplit(propValue);
			if (!matchLength || (splitValues.length == defaultValue.length)) {
				double[] parsedValue = new double[splitValues.length];
				boolean parseError = false;
				for (int i = 0; i < splitValues.length; i++) {
					try {
						parsedValue[i] = Double.parseDouble(splitValues[i].trim());
					} catch (NumberFormatException e) {
						parseError = true;
						Logger logger = Robot.getInstance().getLogger();
						logger.log(Level.WARNING, MessageFormat.format(NFE_MESSAGE, key, splitValues[i]));
					}
				}
				if (!parseError) {
					value = parsedValue;
				}
			}
		}
		return value;
	}

	/**
	 * A utility method to split a configuration string value with commas as
	 * separators into an array of strings.
	 * 
	 * @param propValue
	 *            the string with comma separators to split.
	 * @return the array of separated strings.
	 */
	private static String[] commaSplit(String propValue) {
		return propValue.split(",");
	}
}
