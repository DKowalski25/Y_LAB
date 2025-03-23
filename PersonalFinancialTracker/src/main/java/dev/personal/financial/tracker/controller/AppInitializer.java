package dev.personal.financial.tracker.controller;

import dev.personal.financial.tracker.config.DatabaseConfig;
import dev.personal.financial.tracker.controller.transaction.servlet.TransactionServlet;
import dev.personal.financial.tracker.controller.user.servlet.UserServlet;
import dev.personal.financial.tracker.util.DependencyInjector;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection connection = DatabaseConfig.getConnection();

            DependencyInjector injector = new DependencyInjector(connection);

            ServletFactory servletFactory = new ServletFactory(injector);

            ServletContext context = sce.getServletContext();

            // Регистрируем TransactionServlet
            TransactionServlet transactionServlet = servletFactory.createTransactionServlet();
            context.addServlet("TransactionServlet", transactionServlet)
                    .addMapping("/api/transactions/*");

            // Регистрируем UserServlet
            UserServlet userServlet = servletFactory.createUserServlet();
            context.addServlet("UserServlet", userServlet)
                    .addMapping("/api/users/*");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize servlets", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Очистка ресурсов, если необходимо
    }
}
