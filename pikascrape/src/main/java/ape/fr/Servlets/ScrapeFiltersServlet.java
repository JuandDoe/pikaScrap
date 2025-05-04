package ape.fr.Servlets;

import ape.fr.Main;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.playwright.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ScrapeFiltersServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lire le corps de la requête
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Convertir la chaîne JSON en un objet JSON
        String json = requestBody.toString();
        logger.info("Filtres de l'utilisateur reçus : " + json);

        try {
            // Créer un ObjectMapper pour parser le JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            // Récupérer les valeurs
            int[] countries = objectMapper.convertValue(rootNode.get("countries"), int[].class);
            int[] sellers = objectMapper.convertValue(rootNode.get("sellers"), int[].class);
            int[] languages = objectMapper.convertValue(rootNode.get("languages"), int[].class);
            int[] conditions = objectMapper.convertValue(rootNode.get("conditions"), int[].class);
            String pkmnCardName = objectMapper.convertValue(rootNode.get("pkmCardName"), String.class);

            logger.info("Filtres correctement triés");
            logger.info("Countries: " + Arrays.toString(countries));
            logger.info("Sellers: " + Arrays.toString(sellers));
            logger.info("Languages: " + Arrays.toString(languages));
            logger.info("Conditions: " + Arrays.toString(conditions));

            // Construire l'URL avec les filtres
            String scrapeUrl = pkmnCardName + buildScrapeUrl(countries, sellers, languages, conditions);
            logger.info("URL générée pour le scraping : " + scrapeUrl);

            // Appeler getCheaperPrice avec scrapeUrl
            String cheaperPrice = getCheaperPrice(scrapeUrl);

            // Répondre au client avec l'URL et le prix
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Filters received successfully\", \"scrapeUrl\":\"" + scrapeUrl + "\", \"cheaperPrice\":\"" + cheaperPrice + "\"}");

        } catch (Exception e) {
            logger.error("Erreur lors du traitement des filtres", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON format\"}");
        }
    }

    /**
     * Construit une URL avec les filtres passés en paramètre.
     */
    private String buildScrapeUrl(int[] countries, int[] sellers, int[] languages, int[] conditions) {
        return "?sellerCountry=" + joinArray(countries) +
                "&sellerType=" + joinArray(sellers) +
                "&language=" + joinArray(languages) +
                "&minCondition=" + joinArray(conditions);
    }

    /**
     * Convertit un tableau d'entiers en une chaîne avec des valeurs séparées par des virgules.
     */
    private String joinArray(int[] array) {
        return Arrays.stream(array)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }

    /**
     * Méthode qui prend une URL de scraping et retourne le prix le moins cher.
     */
    private String getCheaperPrice(String scrapeUrl) {
        // Implémente ici la logique pour récupérer le prix depuis l'URL
        logger.info("Récupération du prix pour l'URL : " + scrapeUrl);

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.firefox().launch();
            Scanner input = new Scanner(System.in);
            Page page = browser.newPage();
            page.navigate(scrapeUrl);

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
                logger.info("Clicked on the element with 'id' containing 'articleRow' and a random number: " + targetElement.getAttribute("id"));
            } else {
                logger.info("No elements with 'id' containing 'articleRow' and a random number found.");
            }

            String cardId = targetElement.getAttribute("id");

            String lowestPriceSelector = " > div.col-offer.col-auto > div.price-container.d-none.d-md-flex.justify-content-end > div > div > span";

            String fullId = ("#" + cardId + lowestPriceSelector);
            fullId = fullId.replace(";", "");

            // display the price found into the scraped selector
            String priceCard = page.locator(fullId).innerText();
            logger.info(scrapeUrl);
            logger.info("Lowest card price on cardmarket.com is " + priceCard);

            // Return the price found (important!)
            return priceCard;

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du prix", e);
            return "Error retrieving price";
        }
    }
}
