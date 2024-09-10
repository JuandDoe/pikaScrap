package ape.fr;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public  class Datasources {

    public static void test() {
        // Load the Hikari configuration from the properties file
        HikariConfig config = new HikariConfig("pikascrape/src/main/dasource.properties");

        // Create HikariDataSource using the configuration
        try (HikariDataSource currentDataSource = new HikariDataSource(config)) {

            // Try to get a connection from the pool
            try (Connection conn = currentDataSource.getConnection()) {
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                throw new RuntimeException("Error while trying to get connection", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error initializing the datasource", e);
        }
    }

    public static void main(String[] args) {
        test();
    }
}