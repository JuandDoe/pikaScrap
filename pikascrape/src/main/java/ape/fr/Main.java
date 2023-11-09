package ape.fr;

import com.microsoft.playwright.*;
public class Main {
    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch();
            Page page = browser.newPage();
            page.navigate("https://www.cardmarket.com/en/Pokemon/Products/Singles/151/Venusaur-ex-V1-MEW003");

            // define variable to store the given CSS selector of the page and his content
            String priceCard = page.locator("#articleRow1543373023 > div.col-offer.col-auto > div.price-container.d-none.d-md-flex.justify-content-end > div > div > span").innerText();

            // display the price found into the scraped selector
            System.out.println(priceCard);
        }
    }
}
