plugins {
    kotlin("jvm") version "1.8.10"
    application
}

repositories{
    mavenCentral()
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}


application {
    mainClass.set("MainKt")
}