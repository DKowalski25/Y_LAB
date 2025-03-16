package dev.personal.financial.tracker.db;

import dev.personal.financial.tracker.config.DatabaseConfig;
import dev.personal.financial.tracker.util.EnvLoader;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public class LiquibaseRunner {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    "db/changelog/changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update(new Contexts(), new LabelExpression());
            System.out.println("Database updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
