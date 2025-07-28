plugins {
    id("java")
    id("maven-publish")
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.3.0")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("com.auth0:java-jwt:4.5.0")

    implementation("org.webjars:bootstrap:5.3.5")
    implementation("org.webjars:jquery:3.7.1")
    implementation("org.webjars:font-awesome:6.7.2")

    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("org.slf4j:slf4j-api:2.0.13")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.0")
}

tasks.test {
    useJUnitPlatform()
}
