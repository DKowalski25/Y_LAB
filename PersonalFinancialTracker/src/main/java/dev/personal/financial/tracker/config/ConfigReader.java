package dev.personal.financial.tracker.config;

import dev.personal.financial.tracker.util.EnvReader;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigReader {

    public static Map<String, Object> readConfig(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Config file not found: " + filePath);
            }
            Map<String, Object> config = yaml.load(inputStream);
            replaceEnvVariables(config);
            return config;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read config file: " + filePath, e);
        }
    }

    private static void replaceEnvVariables(Map<String, Object> config) {
        for (Map.Entry<String, Object> entry : config.entrySet()) {
            if (entry.getValue() instanceof String value) {
                if (value.startsWith("${") && value.endsWith("}")) {
                    String envKey = value.substring(2, value.length() - 1);
                    String envValue = EnvReader.getEnv(envKey);
                    if (envValue != null) {
                        config.put(entry.getKey(), envValue);
                    }
                }
            } else if (entry.getValue() instanceof Map) {
                replaceEnvVariables((Map<String, Object>) entry.getValue());
            }
        }
    }
}