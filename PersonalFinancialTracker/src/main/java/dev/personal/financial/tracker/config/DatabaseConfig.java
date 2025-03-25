package dev.personal.financial.tracker.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseConfig {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Map<String, Object> config = ConfigReader.readConfig("application.yml");

        String url = getProperty(config, "datasource.url");
        String username = getProperty(config, "datasource.username");
        String password = getProperty(config, "datasource.password");

        if (url == null || username == null || password == null) {
            throw new RuntimeException("Не удалось найти необходимые параметры в конфигурационном файле.");
        }

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Вспомогательный метод для извлечения значения из вложенной структуры Map.
     *
     * @param config Конфигурация в виде Map.
     * @param key    Ключ в формате "datasource.url" или "datasource.username".
     * @return Значение, соответствующее ключу, или null, если ключ не найден.
     */
    private static String getProperty(Map<String, Object> config, String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = config;

        for (int i = 0; i < keys.length - 1; i++) {
            Object value = current.get(keys[i]);
            if (value instanceof Map) {
                current = (Map<String, Object>) value;
            } else {
                return null;
            }
        }

        Object value = current.get(keys[keys.length - 1]);
        return value != null ? value.toString() : null;
    }
}