package ape.fr;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class accountManager {

    public static HikariDataSource currentDatasource;
    public static String username;
    public static String email;
    public static String telegramId;
    public static String password;
    public static int user_id;

    static int stateOfaccessToPikaScrape;

    public static String loginTry;
    public static String passwordTry;
    public static boolean emailIsCorrect;
    public static boolean passwordIsCorrect;
    public static void testDbConnexion() {
        HikariConfig config = new HikariConfig("pikascrape/src/main/dasource.properties");

        try {
            currentDatasource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing the datasource", e);
        }
    }

    public static void accessToPikaScrape() {
        System.out.println("Choose the option (1/2/3)");
        System.out.println("1) Launch as a guest (You can't use bookmarks)");
        System.out.println("2) Login");
        System.out.println("3) Register");
        Scanner inputAccess = new Scanner(System.in);
        stateOfaccessToPikaScrape = inputAccess.nextInt();
        System.out.println(stateOfaccessToPikaScrape);

        switch (stateOfaccessToPikaScrape) {
            case 1:
                System.out.println("Launching as guest");
                Main.scrapeACard();
                break;
            case 2:
                checkEmailToLoLogIn();
                checkPasswordToLoLogIn();
                Main.scrapeACard();
                break;
            case 3:
                System.out.println("Create an account");
                accountCreate();
                break;
            default:
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
                break;
        }
    }

    public static void accountCreate() {
        System.out.println("Input your account username");
        Scanner inputAccountCreate = new Scanner(System.in);
        username = inputAccountCreate.nextLine();
        System.out.println("Input your account email address");
        email = inputAccountCreate.nextLine();
        System.out.println("Input the Telegram ID you want to link to the current account");
        telegramId = inputAccountCreate.nextLine();
        System.out.println("Input your account password. Please choose a strong one");
        password = inputAccountCreate.nextLine();

        if (currentDatasource != null) {
            try (Connection conn = currentDatasource.getConnection()) {
                DAO instance = new DAO(conn);
                user_id = instance.insertCreateAccount(username, email, telegramId, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Data source not initialized.");
        }
    }

    public static void checkEmailToLoLogIn() {
        while (!emailIsCorrect) {
            System.out.println("Welcome to Pikascrape, please log in into your account");
            System.out.println("input your email address");
            Scanner inputAccess = new Scanner(System.in);
            loginTry = inputAccess.nextLine();

            if (currentDatasource != null) {
                try (Connection conn = currentDatasource.getConnection()) {
                    DAO instance = new DAO(conn);
                    instance.checkLogin(loginTry);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Data source not initialized.");
            }
        }

    }

    public static void checkPasswordToLoLogIn() {
        while (!passwordIsCorrect) {
            System.out.println("input your password");
            Scanner inputAccess = new Scanner(System.in);
            passwordTry = inputAccess.nextLine();

            if (currentDatasource != null) {
                try (Connection conn = currentDatasource.getConnection()) {
                    DAO instance = new DAO(conn);
                    instance.checkPassword(passwordTry);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Data source not initialized.");
            }
        }

    }


    }
