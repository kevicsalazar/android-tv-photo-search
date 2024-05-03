import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "dev.kevinsalazar.tv.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            val accessKey = getProperty("ACCESS_KEY") ?: System.getenv("ACCESS_KEY")
            buildConfigField("String", "ACCESS_KEY", "\"${accessKey}\"")
            buildConfigField("String", "BASE_URL", "\"https://api.unsplash.com/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "ACCESS_KEY", "\"${System.getenv("ACCESS_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"https://api.unsplash.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.json)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.daggerHilt.android)
    ksp(libs.daggerHilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotest)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun getProperty(name: String): String? {
    return try {
        val props = Properties()
        props.load(FileInputStream(rootProject.file("local.properties")))
        props.getProperty(name)
    } catch (e: FileNotFoundException) {
        return null
    }
}
