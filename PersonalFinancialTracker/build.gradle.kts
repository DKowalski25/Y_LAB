import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    java
    kotlin("jvm") version "2.1.20"
    jacoco
    id("com.github.ben-manes.versions") // Плагин для проверки обновлений
    id("org.owasp.dependencycheck") version "8.4.0"
    application
    war
    id("io.freefair.aspectj.post-compile-weaving") version "6.6.1"
}

group = "org.example"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.commons:commons-compress:1.26.0")

    testImplementation(platform(Testing.junit.bom))
    testImplementation(Testing.junit.jupiter)

    implementation("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")

    testImplementation(Testing.mockito.core)

    testImplementation(Testing.assertj.core)

    implementation("io.github.cdimascio:dotenv-java:_")

    implementation("org.postgresql:postgresql:42.7.5")

    implementation("org.liquibase:liquibase-core:_")

    testImplementation("org.testcontainers:testcontainers:_")
    testImplementation("org.testcontainers:junit-jupiter:_")
    testImplementation("org.testcontainers:postgresql:_")

    testRuntimeOnly(Testing.junit.jupiter.engine)
    testImplementation("org.junit.platform:junit-platform-launcher")

    implementation("org.yaml:snakeyaml:2.0")


    implementation("org.hibernate.validator:hibernate-validator:_")
    implementation("org.glassfish:jakarta.el:_") // Для работы hibernate validator

    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:_")

    implementation ("org.aspectj:aspectjrt:_")

}

application {
    mainClass.set("dev.personal.financial.tracker.Main")
}



tasks.war {
    archiveFileName.set("PersonalFinancialTracker.war")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

// Конфигурация задачи dependencyUpdates
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

tasks.register("applyMigrations", JavaExec::class) {
    mainClass.set("dev.personal.financial.tracker.db.LiquibaseRunner")
    classpath = sourceSets.main.get().runtimeClasspath
}

// Функция для определения нестабильных версий
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    return !stableKeyword && !regex.matches(version)
}