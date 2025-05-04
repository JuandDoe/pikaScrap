# Utilise une image Java officielle
FROM openjdk:21

# Dossier de travail dans le conteneur
WORKDIR /app

# Copie les fichiers locaux dans l’image Docker
COPY . .

# Compile le projet avec Gradle
RUN ./gradlew build

# Commande de démarrage : lance l'application Java
CMD ["./gradlew", "run"]