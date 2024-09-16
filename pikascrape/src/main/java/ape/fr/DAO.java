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
