package ape.fr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DAO {

    private static final Logger logger = LoggerFactory.getLogger(DAO.class);

    private final Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    public void checkLogin(String loginTry) {
            String query = "SELECT * FROM account WHERE email = ?";
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                // Préparer la requête avec la connexion à la base de données
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, accountManager.loginTry);  // Passer l'email à vérifier
                resultSet = preparedStatement.executeQuery();
                // Exécuter la requête avec executeQuery() pour un SELECT

                // Vérifier s'il y a une correspondance
                if (resultSet.next()) {
                    System.out.println("L'email est correct.");
                    accountManager.emailIsCorrect = true;
                } else {
                    System.out.println("Email incorrect.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la vérification de l'email", e);
            } finally {
                // Fermer les ressources pour éviter les fuites de mémoire
                try {
                    if (resultSet != null) resultSet.close();
                    if (preparedStatement != null) preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public void checkPassword(String passwordTry) {
        String query = "SELECT * FROM account WHERE password_hash = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Préparer la requête avec la connexion à la base de données
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountManager.passwordTry);  // Passer l'email à vérifier
            resultSet = preparedStatement.executeQuery();
            // Exécuter la requête avec executeQuery() pour un SELECT

            // Vérifier s'il y a une correspondance
            if (resultSet.next()) {
                System.out.println("Le mot de passe est correct.");
                accountManager.passwordIsCorrect = true;
            } else {
                System.out.println("mot de passe incorrect.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du mot de passe", e);
        } finally {
            // Fermer les ressources pour éviter les fuites de mémoire
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int insertCreateAccount(String username, String email, String telegramId, String password) throws SQLException {
        String query = "INSERT INTO account (username, email, telegram_id, password_hash, created_at, updated_at, is_active) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE)";

        int id = 0;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int index = 1;
            preparedStatement.setString(index++, username);
            preparedStatement.setString(index++, email);
            preparedStatement.setString(index++, telegramId);
            preparedStatement.setString(index++, password);
            preparedStatement.executeUpdate();

            generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

        } catch (SQLException exception) {
            logger.error("SQL Exception: ", exception);
        } finally {
            if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) { logger.error("Failed to close ResultSet", e); }
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { logger.error("Failed to close PreparedStatement", e); }
        }

        return id;
    }
}
