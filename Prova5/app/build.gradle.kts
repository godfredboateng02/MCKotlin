plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")

    id("kotlinx-serialization")
}

android {
    namespace = "com.example.prova3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.prova3"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")

    // DataStore (sostituisce SharedPreferences)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Ktor per networking (già presente nel tuo progetto)
    implementation("io.ktor:ktor-client-android:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("io.ktor:ktor-client-logging:2.3.4")

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Compose (probabilmente già presente)
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Coroutines (probabilmente già presente)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Da copiare da qui alla fine
    // Core
    implementation(libs.ktor.ktor.client.core)
    implementation(libs.ktor.client.android)

    // Logging
    implementation(libs.ktor.client.logging)

    // JSON Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.io.ktor.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json.v210)
    implementation(libs.lifecycle.viewmodel.compose)


    //implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.lifecycle.runtime.compose)
    val nav_version = "2.8.0"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.mapbox.maps:android:11.13.0")
    implementation("com.mapbox.extension:maps-compose:11.13.0")
}
