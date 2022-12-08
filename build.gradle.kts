import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "com.shellwen.minestom_demo"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.shellwen.minestom_demo.DemoServerKt")
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Main-Class" to "com.shellwen.minestom_demo.DemoServerKt",
            "Multi-Release" to true
        )
    }
}

repositories {
    maven(url = "https://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.5.0")
    implementation("com.github.Minestom:Minestom:7361bf0825")
    // FUCK Jitpack
    // implementation("com.github.Project-Cepi:KStom:-SNAPSHOT")
//    implementation(":extern:KStom")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}