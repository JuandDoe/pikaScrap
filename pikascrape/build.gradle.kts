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
    implementation("com.microsoft.playwright:playwright:1.39.0");

}

tasks.test {
    useJUnitPlatform()
}