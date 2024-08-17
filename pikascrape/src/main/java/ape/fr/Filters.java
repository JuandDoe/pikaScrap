package ape.fr;

import java.util.HashMap;

public class Filters {

   public static HashMap<String, Integer> sellersLocations = new HashMap<String, Integer>();
    public static HashMap<String, Integer> sellersTypes = new HashMap<String, Integer>();
    public static HashMap<String, Integer> cardsLanguages = new HashMap<String, Integer>();
    public static HashMap<String, Integer> cardMinConditions = new HashMap<String, Integer>();
    public static HashMap<String, Boolean> Extra = new HashMap<String, Boolean>();

    // Example : ?sellerCountry=15

   public static void putInHMapSellerLocations () {
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
   }

   // example : ?sellerType=0

    public static void putInHMapSellersTypes() {
        sellersTypes.put("Pivate", 0);
        sellersTypes.put("Professional", 1);
        sellersTypes.put("Powers1ller", 2);
    }

    // example : ?language=12
    public static void putInHMapcardsLanguages() {
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
    }

    // example : ?minCondition=6
    public static void putInHMapcardMinConditions() {

       cardMinConditions.put("Mint", 1);
        cardMinConditions.put("Near Mint", 2);
        cardMinConditions.put("Excellent", 3);
        cardMinConditions.put("Good", 4);
        cardMinConditions.put("Light Played", 5);
        cardMinConditions.put("Played", 6);
    }

    public static void putInHMapExtras() {

       Extra.put("isSignedY", Boolean.TRUE);
       Extra.put("isSignedN", Boolean.FALSE);
       Extra.put("isFirstEditionY", Boolean.TRUE);
       Extra.put("isFirstEditionN", Boolean.FALSE);
       Extra.put("isPlaysetY", Boolean.TRUE);
       Extra.put("isPlaysetN", Boolean.FALSE);
       Extra.put("isAlteredY", Boolean.TRUE);
       Extra.put("isAlteredN", Boolean.FALSE);

    }
}
