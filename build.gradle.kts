import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
}

val kotlinVersion = "1.7.10"
val springBootVersion = "2.7.2"
val exposedVersion ="0.38.2"
val postgresVersion = "42.3.6"
val jjwtVersion = "0.9.1"
val jaxbApiVersion = "2.4.0-b180830.0359"
val junitVersion = "5.8.2"
val assertjVersion = "3.22.0"
val mockitoKotlinVersion = "1.6.0"
val datafakerVersion = "1.5.0"
val krushVersion = "1.0.0"
val testcontainersVersion = "1.17.3"

group = "io.realworld"
version = "0.2.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.jetbrains.exposed:spring-transaction:$exposedVersion")
    implementation("org.flywaydb:flyway-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.jsonwebtoken:jjwt:$jjwtVersion")
    implementation("javax.xml.bind:jaxb-api:$jaxbApiVersion")
    implementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    implementation("pl.touk.krush:krush-runtime:$krushVersion")
    implementation("pl.touk.krush:krush-annotation-processor:$krushVersion")
    kapt("pl.touk.krush:krush-annotation-processor:$krushVersion")

    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("com.nhaarman:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("net.datafaker:datafaker:$datafakerVersion")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.postgresql:postgresql:$postgresVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
