plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation ("org.mockito:mockito-core:5.5.0")

    testImplementation ("org.assertj:assertj-core:3.24.2")
}

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.test {
    useJUnitPlatform()
}