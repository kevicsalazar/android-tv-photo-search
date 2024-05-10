plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.detekt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.androidx.paging.common)

    testImplementation(libs.junit)
    testImplementation(libs.kotest)
}