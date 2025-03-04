package ape.fr.Servlets;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import ape.fr.utils.Out;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.Serial;

import static ape.fr.Main.logger;
import static ape.fr.java.conf.ConfigurationController.*;

public class HomeMenuServlet extends HttpServlet {


    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_CONF = "dasource.properties";
    public static HikariDataSource datasource;
    public static String defaultJdbcUrl = JDBC_URL.getDefaultValue();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"status\": \"Hello World!. This is the home menu.\"}");
    }

    public static void connectDB() throws ConfigurationException, IllegalAccessException {
        String configFilename = HomeMenuServlet.DEFAULT_CONF;

        // Chargement de la configuration
        logger.info("Chargement du fichier de configuration : " + configFilename);
        addConfiguration(new PropertiesConfiguration(configFilename));

        logger.info("Configuration utilisée: \n" + printToString());

        // Vérification de la configuration
        Out<String> message = new Out<>();
        checkPrerequisites(message);
        logger.info("Vérification de la configuration: \n" + message.get());

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getString(JDBC_URL));
        config.setUsername(getString(DB_USERNAME));
        config.setPassword(getString(DB_PASSWORD));

        config.setMaximumPoolSize(1);
        datasource = new HikariDataSource(config);

        logger.info("Connexion à " + (getString(JDBC_URL)));

    }
}


