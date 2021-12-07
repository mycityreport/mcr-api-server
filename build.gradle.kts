plugins {
    application
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    java
}

group = "info.mycityreport"
version = "0.0.1"

repositories {
    mavenCentral()
}

val kotlinVersion = "1.6.0"
val ktorVersion = "1.6.6"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation("io.ktor:ktor-server-core:${ktorVersion}")
    implementation("io.ktor:ktor-server-cio:${ktorVersion}")
    implementation("io.ktor:ktor-serialization:${ktorVersion}")
    implementation("ch.qos.logback:logback-classic:1.2.7")

    testImplementation("io.ktor:ktor-server-test-host:${ktorVersion}")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${kotlinVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
