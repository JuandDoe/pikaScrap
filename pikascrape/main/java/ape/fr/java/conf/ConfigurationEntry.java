package ape.fr.java.conf;

import org.apache.commons.lang3.StringUtils;

public class ConfigurationEntry<T> {

    private final String key;
    private T defaultValue;
    private boolean isRequired;
    private final String description;
    private final String type;

    public ConfigurationEntry(String key, T defaultValue, boolean isRequired, String description, Class<T> type) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.isRequired = isRequired;
        this.description = description;
        this.type = type.getSimpleName();
    }

    public ConfigurationEntry(String key, String description, Class<T> type) {
        this(key, null, false, description, type);
    }

    public String getKey() {
        return key;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(obj instanceof ConfigurationEntry<?>) {
            ConfigurationEntry<?> entry = (ConfigurationEntry<?>) obj;
            return StringUtils.equals(entry.getKey(), this.getKey());
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("key: %s, defaultValue: %s, isRequired: %s", getKey(), getDefaultValue(), isRequired());
    }
}

