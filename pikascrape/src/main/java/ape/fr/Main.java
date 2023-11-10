package ape.fr;

import com.microsoft.playwright.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch();
            Scanner input = new Scanner(System.in);
            System.out.println("Go to https://www.cardmarket.com/en/Pokemon/Products/Singles and chose a card chose a card\n " +
                    "imput everything after \"Singles\" in the url of the card you want to scrape\n " +
                    "Example : for 'https://www.cardmarket.com/en/Pokemon/Products/Singles/151/Venusaur-ex-V1-MEW003' => '/151/Venusaur-ex-V1-MEW003'\n");

            // trim() erase the " " character caused by typing "enter" after .nextLine
            String cardCustomLink = input.nextLine().trim();

            final String baseLink = "https://www.cardmarket.com/en/Pokemon/Products/Singles;";

            // Erase ";" caused by Strings concatenation
            String fullCustomLink = (baseLink + cardCustomLink).replace(";", "");

            Page page = browser.newPage();
            page.navigate(fullCustomLink);

            // define variable to store the given CSS selector of the page and his content
            String priceCard = page.locator("#articleRow1543511636 > div.col-offer.col-auto > div.price-container.d-none.d-md-flex.justify-content-end > div > div > span").innerText();

            // display the price found into the scraped selector
            System.out.println(priceCard);
        }
    }


}
