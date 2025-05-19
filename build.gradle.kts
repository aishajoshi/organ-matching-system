plugins {
    id("java")
    id("maven-publish")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j:8.3.0")

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("org.springframework.boot:spring-boot-starter-tomcat")

    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.webjars:bootstrap:5.3.0")
    implementation("org.webjars:jquery:3.6.4")
    implementation("org.webjars:font-awesome:6.4.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
