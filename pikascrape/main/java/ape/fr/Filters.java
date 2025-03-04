package ape.fr;

import java.util.HashMap;
import java.util.Scanner;

public class Filters {

    public static HashMap<String, Integer> sellersLocations = new HashMap<String, Integer>();
    public static HashMap<String, Integer> sellersTypes = new HashMap<String, Integer>();
    public static HashMap<String, Integer> cardsLanguages = new HashMap<String, Integer>();
    public static HashMap<String, Integer> cardsMintMinConditions = new HashMap<String, Integer>();
    public static HashMap<String, Boolean> Extra = new HashMap<String, Boolean>();

    public static int stateOfSellerLocationFilter;
    public static String userChoiceSellerLocations;
    public static int stateOfSellerTypesFilter;
    public static String userChoiceSellerTypes;
    public static int stateOfCardLanguageFilter;
    public static String userChoiceCardLanguage;
    public static int stateOfMinimumMintConditionFilter;
    public static String userChoiceMinimumMintCondition;
    public static int stateOfExtraFilter;
    public static String userChoiceExtra;

    public static void putInFiltersHTables() {

        // Example : ?sellerCountry=15
        sellersLocations.put("Austria", 1);
        sellersLocations.put("Belgium", 2);
        sellersLocations.put("Bulgaria", 3);
        sellersLocations.put("Canada", 33);
        sellersLocations.put("Croatia", 35);
        sellersLocations.put("Cyprus", 5);
        sellersLocations.put("Czech Republic", 6);
        sellersLocations.put("Denmark", 8);
        sellersLocations.put("Estonia", 9);
        sellersLocations.put("Finland", 11);
        sellersLocations.put("France", 12);
        sellersLocations.put("Germany", 7);
        sellersLocations.put("Greece", 14);
        sellersLocations.put("Hungary", 15);
        sellersLocations.put("Iceland", 37);
        sellersLocations.put("Ireland", 16);
        sellersLocations.put("Italy", 17);
        sellersLocations.put("Japan", 36);
        sellersLocations.put("Latvia", 21);
        sellersLocations.put("Liechtenstein", 18);
        sellersLocations.put("Lithuania", 19);
        sellersLocations.put("Luxembourg", 20);
        sellersLocations.put("Malta", 21);
        sellersLocations.put("Netherlands", 23);
        sellersLocations.put("Norway", 24);
        sellersLocations.put("Poland", 25);
        sellersLocations.put("Portugal", 26);
        sellersLocations.put("Romania", 27);
        sellersLocations.put("Singapore", 29);
        sellersLocations.put("Slovakia", 31);
        sellersLocations.put("Slovenia", 30);
        sellersLocations.put("Spain", 10);
        sellersLocations.put("Sweden", 28);
        sellersLocations.put("Switzerland", 4);
        sellersLocations.put("United Kingdom", 13);

        // example : ?sellerType=0
        sellersTypes.put("Pivate", 0);
        sellersTypes.put("Professional", 1);
        sellersTypes.put("Powers1ller", 2);

        // example : ?language=12
        cardsLanguages.put("English", 1);
        cardsLanguages.put("French", 2);
        cardsLanguages.put("German", 3);
        cardsLanguages.put("Spanish", 4);
        cardsLanguages.put("Italian", 5);
        cardsLanguages.put("S-Chinese", 6);
        cardsLanguages.put("Japanese", 7);
        cardsLanguages.put("Portuguese", 8);
        cardsLanguages.put("Russian", 9);
        cardsLanguages.put("Korean", 10);
        cardsLanguages.put("T-Chinese", 11);
        cardsLanguages.put("Dutch", 12);
        cardsLanguages.put("Polish", 13);
        cardsLanguages.put("Czech", 14);
        cardsLanguages.put("Hungarian", 15);

        // example : ?minCondition=6
        cardsMintMinConditions.put("Mint", 1);
        cardsMintMinConditions.put("Near Mint", 2);
        cardsMintMinConditions.put("Excellent", 3);
        cardsMintMinConditions.put("Good", 4);
        cardsMintMinConditions.put("Light Played", 5);
        cardsMintMinConditions.put("Played", 6);

        Extra.put("isSignedY", Boolean.TRUE);
        Extra.put("isSignedN", Boolean.FALSE);
        Extra.put("isFirstEditionY", Boolean.TRUE);
        Extra.put("isFirstEditionN", Boolean.FALSE);
        Extra.put("isPlaysetY", Boolean.TRUE);
        Extra.put("isPlaysetN", Boolean.FALSE);
        Extra.put("isAlteredY", Boolean.TRUE);
        Extra.put("isAlteredN", Boolean.FALSE);
    }

    public static String userSellerCountryFilter() {
        while (true) {
            System.out.println("Sort cards sellers by countries ? (1)Yes/(0)No");
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                stateOfSellerLocationFilter = input.nextInt();
                input.nextLine();

                if (stateOfSellerLocationFilter == 1) {
                    System.out.println(sellersLocations);
                    System.out.println("Chose seller location : to sort cards from France and Germany, input : 12,7,");
                    userChoiceSellerLocations = input.nextLine();
                    return userChoiceSellerLocations;

                } else if (stateOfSellerLocationFilter == 0) {
                    System.out.println(" WARNING : Seller locations filter is  DESACTIVATE ");
                    break;
                } else {
                    System.out.println("ERROR : Imput 1 for Yes or 0 for No");
                }
            }
        }
        return "";
    }
    public static String userSellerTypesFilter() {
        while (true) {
            System.out.println("Sort cards sellers by sellers types ? (1)Yes/(0)No");
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                stateOfSellerTypesFilter = input.nextInt();
                input.nextLine();

                if (stateOfSellerTypesFilter == 1) {
                    System.out.println(sellersTypes);
                    System.out.println("Chose seller type : to sort cards from professionals and powersellers, input : 1,2,");
                    userChoiceSellerTypes = input.nextLine();
                    return userChoiceSellerTypes;

                } else if (stateOfSellerTypesFilter == 0) {
                    System.out.println(" WARNING : Seller types filter is  DESACTIVATE ");
                    break;
                } else {
                    System.out.println("ERROR : Imput 1 for Yes or 0 for No");
                }
            }
        }
        return "";
    }

    public static String userCardLanguageFilter() {
        while (true) {
            System.out.println("Sort cards by languages ? (1)Yes/(0)No");
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                stateOfCardLanguageFilter = input.nextInt();
                input.nextLine();

                if (stateOfCardLanguageFilter == 1) {
                    System.out.println(cardsLanguages);
                    System.out.println("Chose card languages : to sort cards in french and german, input : 2,3,");
                    userChoiceCardLanguage = input.nextLine();
                    return userChoiceCardLanguage;

                } else if (stateOfCardLanguageFilter == 0) {
                    System.out.println(" WARNING : Card languages filter is  DESACTIVATE ");
                    break;
                } else {
                    System.out.println("ERROR : Imput 1 for Yes or 0 for No");
                }
            }
        }
        return "";
    }

    public static String userCardMinConditionsFilter() {
        while (true) {
            System.out.println("Sort cards sellers by mint condition ? (1)Yes/(0)No");
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                stateOfMinimumMintConditionFilter = input.nextInt();
                input.nextLine();

                if (stateOfMinimumMintConditionFilter == 1) {
                    System.out.println(cardsMintMinConditions);
                    System.out.println("Chose seller location : to sort cards only with near mint or better mint condition, input : 2,");
                    userChoiceMinimumMintCondition = input.nextLine();
                    return userChoiceMinimumMintCondition;

                } else if (stateOfMinimumMintConditionFilter == 0) {
                    System.out.println(" WARNING : Minimum mint condition filter is  DESACTIVATE ");
                    break;
                } else {
                    System.out.println("ERROR : Imput 1 for Yes or 0 for No");
                }
            }
        }
        return "";
    }
    }

// serveur mail pour reset de mdp
// web assembly (faire tourner programme dedpuis le web)
//faker (données de démo)
// https://github.com/luttje/css-pokemon-gameboy/    pokemon style
// multi threading pour commande /bye quitter l'application
