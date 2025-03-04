package ape.fr;

import ape.fr.Servlets.ConnexionServlet;
import ape.fr.Servlets.NewBookmarkServlet;
import ape.fr.Servlets.ScrapeServlet;
import ape.fr.Servlets.SignUpServlet;
import ape.fr.utils.Out;
import com.microsoft.playwright.*;
import java.sql.SQLException;
import java.util.HashMap;
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
import jakarta.servlet.http.HttpServlet;


public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static String fullCustomLink;


    public static void main(String[] args) throws SQLException {




        //public static final String SAVE_DIR_AUDIO= String.valueOf(SAVE_DIR_AUDIO);
            // Initialisation du serveur
            Server server = new Server(9090);

            try {
                // Configuration du contexte et des servlets
                ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                context.setContextPath("/");

                context.addServlet(String.valueOf(ConnexionServlet.class), "/connexion");
                context.addServlet(String.valueOf(NewBookmarkServlet.class), "/existingtranscription/*");
                context.addServlet(String.valueOf(ScrapeServlet.class), "/newtranscription");
                context.addServlet(String.valueOf(SignUpServlet.class), "/waitedtranscription");

                server.setHandler(context);

                // Démarrage du serveur
                logger.info("Starting Jetty server on port 9090...");
                connectDB();
                server.start();

                // Maintenir le serveur actif
                logger.info("Hint: Hit Ctrl+C to stop Jetty.");
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


        //putInFiltersHTables();
        //accountManager.testDbConnexion();
       // accountManager.accessToPikaScrape();



    public static void scrapeACard() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.firefox().launch();
            Scanner input = new Scanner(System.in);
            System.out.println("Go to https://www.cardmarket.com/en/Pokemon/Products/Singles and chose a card chose a card\n " +
                    "imput everything after \"Singles\" in the url of the card you want to scrape\n " +
                    "Example : for 'https://www.cardmarket.com/en/Pokemon/Products/Singles/151/Venusaur-ex-V1-MEW003' => '/151/Venusaur-ex-V1-MEW003'\n");

            // trim() erase the " " character caused by typing "enter" after .nextLine

            final String baseLink = "https://www.cardmarket.com/en/Pokemon/Products/Singles;";

            String cardCustomLink = input.nextLine().trim();

            String sellerLocationChoseByUser = "?sellerCountry=" + Filters.userSellerCountryFilter();

            // Erase ";" caused by Strings concatenation
            fullCustomLink = (baseLink + cardCustomLink + sellerLocationChoseByUser).replace(";", "");

            String sellerTypeChoseByUser = "&sellerType=" + Filters.userSellerTypesFilter();

            if (stateOfSellerTypesFilter == 1) {

                fullCustomLink = (fullCustomLink + sellerTypeChoseByUser).replace(";", "");

                System.out.println(fullCustomLink);
            }

            String cardLanguageChoseByUser = "&language=" + Filters.userCardLanguageFilter();
            if (stateOfCardLanguageFilter == 1) {
                fullCustomLink = (fullCustomLink + cardLanguageChoseByUser).replace(";", "");
            }

            String cardMintMinConditionChoseByUser = "&minCondition=" + Filters.userCardMinConditionsFilter();
            if (stateOfMinimumMintConditionFilter == 1) {
                fullCustomLink = (fullCustomLink + cardMintMinConditionChoseByUser).replace(";", "");
            }

            Page page = browser.newPage();
            page.navigate(fullCustomLink);

            // Find all elements with "id" attributes
            ElementHandle[] elements = page.querySelectorAll("[id]").toArray(new ElementHandle[0]);

            // Iterate through the elements and find the first one that matches the criteria
            ElementHandle targetElement = null;

            for (ElementHandle element : elements) {
                String idAttributeValue = element.getAttribute("id");

                // Check if the id contains "articleRow" followed by a random number
                if (idAttributeValue != null && idAttributeValue.matches("articleRow\\d+")) {
                    targetElement = element;
                    break; // Stop searching when a matching element is found
                }
            }

            if (targetElement != null) {
                targetElement.click();
                System.out.println("Clicked on the element with 'id' containing 'articleRow' and a random number: " + targetElement.getAttribute("id"));
            } else {
                System.out.println("No elements with 'id' containing 'articleRow' and a random number found.");
            }

            String cardId = targetElement.getAttribute("id");

            String lowestPriceSelector = " > div.col-offer.col-auto > div.price-container.d-none.d-md-flex.justify-content-end > div > div > span";

            String fullId = ("#" + cardId + lowestPriceSelector);
            fullId = fullId.replace(";", "");

            // display the price found into the scraped selector
            String priceCard = page.locator(fullId).innerText();
            System.out.println(fullCustomLink);
            System.out.println("Lowest card price on cardmarket.com is " + priceCard);
        }
    }
}