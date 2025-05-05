package ape.fr;

import ape.fr.Servlets.ConnexionServlet;
import ape.fr.Servlets.NewBookmarkServlet;
import ape.fr.Servlets.ScrapeFiltersServlet;
import ape.fr.Servlets.SignUpServlet;
import ape.fr.utils.Out;
import com.microsoft.playwright.*;
import java.sql.SQLException;
import java.util.Scanner;
import static ape.fr.Filters.*;
import static ape.fr.Servlets.HomeMenuServlet.connectDB;
import static ape.fr.java.conf.ConfigurationController.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ConfigurationException;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static String fullCustomLink;

    public static void main(String[] args) throws SQLException {
        // Récupère le port depuis l'environnement (Render) ou utilise 9090 en local
        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "9090")
        );

        // Initialisation du serveur sur le port dynamique
        Server server = new Server(port);

        try {
            // Configuration du contexte et des servlets
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");

            context.addServlet(ConnexionServlet.class, "/connexion");
            context.addServlet(NewBookmarkServlet.class, "/newbookmark");
            context.addServlet(ScrapeFiltersServlet.class, "/scrape");
            context.addServlet(SignUpServlet.class, "/signup");

            server.setHandler(context);

            // Démarrage du serveur
            logger.info("Starting Jetty server on port {}...", port);
            connectDB();
            server.start();

            // Maintenir le serveur actif
            logger.info("Server running. Press Ctrl+C to stop Jetty.");
            server.join();

        } catch (Exception e) {
            logger.error("An error occurred while starting the server.", e);
        } finally {
            try {
                server.stop();
                logger.info("Jetty server stopped.");
            } catch (Exception stopException) {
                logger.error("Error while stopping the server.", stopException);
            }
        }
    }

    public static void scrapeACard() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch();
            Scanner input = new Scanner(System.in);
            logger.info("Go to https://www.cardmarket.com/... and enter the card path.");

            final String baseLink = "https://www.cardmarket.com/en/Pokemon/Products/Singles";
            String cardCustomLink = input.nextLine().trim();

            String sellerLocation = "?sellerCountry=" + Filters.userSellerCountryFilter();
            fullCustomLink = (baseLink + cardCustomLink + sellerLocation).replace(";", "");

            if (stateOfSellerTypesFilter == 1) {
                fullCustomLink += "&sellerType=" + Filters.userSellerTypesFilter();
            }
            if (stateOfCardLanguageFilter == 1) {
                fullCustomLink += "&language=" + Filters.userCardLanguageFilter();
            }
            if (stateOfMinimumMintConditionFilter == 1) {
                fullCustomLink += "&minCondition=" + Filters.userCardMinConditionsFilter();
            }

            Page page = browser.newPage();
            page.navigate(fullCustomLink);

            ElementHandle[] elements = page.querySelectorAll("[id]").toArray(new ElementHandle[0]);
            ElementHandle targetElement = null;
            for (ElementHandle element : elements) {
                String idValue = element.getAttribute("id");
                if (idValue != null && idValue.matches("articleRow\\d+")) {
                    targetElement = element;
                    break;
                }
            }

            if (targetElement != null) {
                targetElement.click();
                logger.info("Clicked on element with id: {}", targetElement.getAttribute("id"));
                String fullId = "#" + targetElement.getAttribute("id") + " > div.col-offer... span";
                String priceCard = page.locator(fullId).innerText();
                logger.info("Lowest card price: {}", priceCard);
            } else {
                logger.info("No matching element found.");
            }
        }
    }
}
