plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "dev.kevinsalazar.tv.photosearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.kevinsalazar.tv.photosearch"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = System.getenv("RELEASE_KEYSTORE_ALIAS")
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            storeFile = file("../keystore.jks")
            storePassword = System.getenv("RELEASE_KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
    detekt {
        toolVersion = "1.23.6"
        config.setFrom(file("config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.daggerHilt.compose)
    implementation(libs.daggerHilt.android)
    ksp(libs.daggerHilt.compiler)

    implementation(libs.kotlinDateTime)
    implementation(libs.kotlinCoroutines)
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    testImplementation(libs.kotest)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}