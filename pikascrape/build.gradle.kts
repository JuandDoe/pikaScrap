plugins {
    id("java")
}

group = "ape.fr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // playwright (emulate browser + scrape)
    implementation("com.microsoft.playwright:playwright:1.46.0");

    // database optimisation
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:5.1.0")

    implementation("org.postgresql:postgresql:42.7.4") // Adjust the version as needed

    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation("ch.qos.logback:logback-classic:1.5.8")

}

tasks.test {
    useJUnitPlatform()
}