package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Enhanced configuration manager with better error handling and validation
 * Author: Sivaraman M
 */
public class ConfigManager {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static Properties properties;
    private static ConfigManager instance;

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            validateRequiredProperties();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    private void validateRequiredProperties() {
        String[] requiredKeys = {"baseURL", "email", "password"};
        for (String key : requiredKeys) {
            if (getProperty(key) == null || getProperty(key).trim().isEmpty()) {
                throw new RuntimeException("Required property '" + key + "' is missing or empty in config.properties");
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // Convenience methods for common properties
    public String getBaseUrl() {
        return getProperty("baseURL");
    }

    public String getEmail() {
        return getProperty("email");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public int getExplicitWait() {
        return getIntProperty("explicit.wait", 15);
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }
}