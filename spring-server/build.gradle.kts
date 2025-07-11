plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.2.0"
    alias(libs.plugins.ksp)
    alias(libs.plugins.graalvm.buildtools.native)
    id("org.springframework.boot") version "3.5.3" // "4.0.0-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "pro.walkin.memos"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    ksp(libs.komapper.processor)
    ksp("pro.walkin.logging:processor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation(libs.springdoc)

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("org.springframework.session:spring-session-core")
    implementation("com.auth0:java-jwt:4.5.0")

    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation(libs.hikaricp)
    implementation(libs.kotlinx.datetime)
    implementation(libs.komapper.spring.starter)
    implementation(libs.komapper.dialect.postgresql)
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("pro.walkin.logging:core")

    implementation("org.liquibase:liquibase-core")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

ksp {
    arg("translationFilesPath", "${project.projectDir}/i18n/")
}
