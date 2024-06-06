package Pages;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader instance = null;
    private Properties props = null;

    private ConfigReader() {
        try (InputStream pis = this.getClass().getResourceAsStream("/config.properties")) {
            props = new Properties();
            props.load(pis);
        } catch (Exception ignored) {

        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getProperty(String propName) {
        return instance.props.getProperty(propName);
    }
}
