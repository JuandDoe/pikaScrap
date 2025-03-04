package ape.fr.java.conf;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ape.fr.utils.Out;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

public class ConfigurationController {

    public static final ape.fr.java.conf.ConfigurationEntry<String> DB_PASSWORD = new ape.fr.java.conf.ConfigurationEntry<>(
            "pikascrap.db.password", "putPasswordHere", true,
            "Mot de passe de la base de donnée", String.class);

    public static final ape.fr.java.conf.ConfigurationEntry<String> JDBC_URL = new ape.fr.java.conf.ConfigurationEntry<>(
            "pikascrap.db.jdbcUrl", "jdbc:postgresql://localhost:543222/pikascrape", true,
            "url de la base de donnée pikascrap", String.class);

    public static final ape.fr.java.conf.ConfigurationEntry<String> DB_USERNAME = new ape.fr.java.conf.ConfigurationEntry<>(
            "pikascrap.db.username", "putUsernameHere", true,
            "Nom de la base de données", String.class);

    private static final CompositeConfiguration configuration = new CompositeConfiguration();
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    public static void addConfiguration(Configuration configuration) {
        ConfigurationController.configuration.addConfiguration(configuration);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static Object getProperty(String key) {
        return configuration.getProperty(key);
    }

    public static Object getProperty(ape.fr.java.conf.ConfigurationEntry<Object> param) {
        return getProperty(param.getKey());
    }

    public static String getString(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    public static String getString(ape.fr.java.conf.ConfigurationEntry<String> param) {
        return getString(param.getKey(), param.getDefaultValue());
    }

    public static String printToString() {
        StringBuilder result = new StringBuilder();

        for (Iterator<String> it = configuration.getKeys(); it.hasNext();) {
            String key = it.next();
            result.append(key).append(" = ").append(getProperty(key)).append("\n");
        }

        return result.toString();
    }

    public static boolean checkPrerequisites(Out<String> details) throws IllegalAccessException {
        StringBuilder message = new StringBuilder();
        boolean prerequisitesFulfilled = true;

        // On parcourt les champs statics du type ConfigurationEntry
        for (Field field : ConfigurationController.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType().isAssignableFrom(ape.fr.java.conf.ConfigurationEntry.class)) {
                ape.fr.java.conf.ConfigurationEntry<?> entry = (ape.fr.java.conf.ConfigurationEntry<?>) field.get(null);

                // On vérifie que le prérequis est remplis
                Object value = getProperty(entry.getKey()) != null ? getProperty(entry.getKey())
                        : entry.getDefaultValue();
                boolean prerequisiteFulfilled = !entry.isRequired() || value != null;

                // On affiche l'état du prérequis
                message.append(entry.getKey()).append(" = ").append(value != null ? value.toString() : "null")
                        .append(" [").append(prerequisiteFulfilled ? "OK" : "VALEUR REQUISE").append("]").append("\n");

                // On met à jour l'état global des prérequis
                prerequisitesFulfilled &= prerequisiteFulfilled;
            }
        }

        details.set(message.toString());
        return prerequisitesFulfilled;
    }
}

