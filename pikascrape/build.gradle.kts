plugins {
    application
}

group = "ape.fr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Playwright (emulate browser + scrape)
    implementation("com.microsoft.playwright:playwright:1.46.0")

    // Database optimisation
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.postgresql:postgresql:42.7.4")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.8")

    // Logback pour la gestion des logs
    implementation ("ch.qos.logback:logback-core:1.4.14") // https://mvnrepository.com/artifact/org.openjfx/javafx-graphics
    implementation("org.openjfx:javafx-graphics:23")
    implementation ("ch.qos.logback:logback-classic:1.4.12")

    // Janino pour le support des expressions dynamiques dans Logback
    implementation ("org.codehaus.janino:janino:3.1.2")

    // Jetty (serveur et servlets)
    implementation ("org.eclipse.jetty:jetty-server:12.0.14")
    implementation ("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.0.14")
    implementation ("org.eclipse.jetty:jetty-servlet:11.0.15")
    implementation ("org.eclipse.jetty:jetty-annotations:11.0.15")
    implementation ("org.eclipse.jetty:jetty-plus:11.0.15")
    compileOnly ("javax.servlet:javax.servlet-api:4.0.1")
    implementation ("javax.annotation:javax.annotation-api:1.3.2")

    // Jackson pour le traitement JSON
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.18.2")


    // https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-client
    implementation ("org.eclipse.jetty:jetty-client:12.0.16")

    // Apache Commons
    implementation ("org.apache.commons:commons-lang3:3.17.0")

    // Apache Commons Configuration
    implementation ("commons-configuration:commons-configuration:1.10")


}






