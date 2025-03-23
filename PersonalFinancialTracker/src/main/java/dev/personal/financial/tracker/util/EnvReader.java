package dev.personal.financial.tracker.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvReader {
    private static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure()
                .directory("../")
                .filename(".env")
                .ignoreIfMissing()
                .load();

        System.out.println("✅ .env файл загружен!");
    }

    public static String getEnv(String key) {
        String value = dotenv.get(key);
        if (value == null) {
            System.out.println("⚠ Переменная " + key + " отсутствует в .env");
        } else {
            System.out.println("🔹 Переменная " + key + " = " + value);
        }
        return value;
    }
}
