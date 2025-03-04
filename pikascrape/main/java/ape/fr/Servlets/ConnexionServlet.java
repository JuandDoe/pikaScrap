package ape.fr.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ConnexionServlet extends HttpServlet {

    // Classe interne pour stocker les informations de l'utilisateur
    public static class User {
        public String name;
        public String password;

        // Constructeur sans argument (nécessaire pour Jackson)
        public User() {}

        // Getters et setters (si nécessaires)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lire le corps de la requête
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Convertir la chaîne JSON en un objet User
        String json = requestBody.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(json, User.class);

        // Traiter les données de l'utilisateur
        System.out.println("Received user: " + user.getName() + ", password: " + user.getPassword());

        // Répondre au client (tu peux aussi renvoyer un JSON)
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\":\"User received successfully\"}");
    }

}