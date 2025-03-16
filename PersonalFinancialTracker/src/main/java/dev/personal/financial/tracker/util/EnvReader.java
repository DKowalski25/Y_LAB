package dev.personal.financial.tracker.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvReader {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getEnv(String key) {
        String value = dotenv.get(key);
        if (value == null) {
            System.out.println("⚠ Environment variable " + key + " is not in .env file");
        }
        return value;
    }
}
