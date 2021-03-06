import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    id("com.github.gmazzo.buildconfig") version "2.0.2"
}
val group = "net.sebastianbeckmann"
val version = "1.0-SNAPSHOT"

/* execute "./gradlew generateBuildConfig" */
buildConfig {
    buildConfigField("String", "VERSION", "\"$version\"")
}

repositories {
    mavenCentral()
    jcenter()
}
dependencies {
    testImplementation(kotlin("test-junit"))
    implementation("net.dv8tion:JDA:4.2.0_214")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "core.MainKt"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
