DROP TRIGGER IF EXISTS update_account_modtime ON account;
DROP TRIGGER IF EXISTS update_bookmark_modtime ON bookmark;

-- Supprimer la fonction si elle existe déjà
DROP FUNCTION IF EXISTS update_modified_column();

-- Création des tables
CREATE TABLE IF NOT EXISTS account (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telegram_id VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS bookmark (
    bookmark_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES account(user_id) ON DELETE CASCADE
);

-- Création de la fonction pour mettre à jour le timestamp
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Vérifier et créer les déclencheurs si nécessaire
DO $$
BEGIN
    -- Vérifier si le déclencheur update_account_modtime existe déjà
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = 'update_account_modtime'
    ) THEN
        CREATE TRIGGER update_account_modtime
        BEFORE UPDATE ON account
        FOR EACH ROW
        EXECUTE FUNCTION update_modified_column();
    END IF;

    -- Vérifier si le déclencheur update_bookmark_modtime existe déjà
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = 'update_bookmark_modtime'
    ) THEN
        CREATE TRIGGER update_bookmark_modtime
        BEFORE UPDATE ON bookmark
        FOR EACH ROW
        EXECUTE FUNCTION update_modified_column();
    END IF;
END $$;