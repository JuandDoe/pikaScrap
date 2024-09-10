package ape.fr;

import java.util.Scanner;

public class accountManager {

    static int stateOfaccessToPikaScrape;

    public static void accessToPikaScrape() {

        System.out.println("Chose the option (1/2/3)");
        System.out.println("1)Launch as a guest (You can't use bookmarks)");
        System.out.println("2)Login");
        System.out.println("3)Register");
        Scanner input = new Scanner(System.in);
        stateOfaccessToPikaScrape = input.nextInt();

        while (true){

            if (stateOfaccessToPikaScrape == 1){
                System.out.println("Launching as guest");
                Main.scrapeACard();
                input.nextLine();

                if (stateOfaccessToPikaScrape == 2){
                    System.out.println("Log into your account");
                    Main.scrapeACard();
                    input.nextLine();

                    if (stateOfaccessToPikaScrape == 3){
                        System.out.println("Create an account");
                        Main.scrapeACard();
                        input.nextLine();

                    }
                }
            }
        }
    }
}