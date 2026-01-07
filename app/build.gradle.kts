plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Menggunakan plugin Compose terbaru (ini standar baru di Master)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.alp_vp_frontend"
    // IKUT MASTER: Pakai SDK 35 biar aman pas merge nanti
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.alp_vp_frontend"
        minSdk = 24
        // IKUT MASTER: Target 35
        targetSdk = 35
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

    // IKUT MASTER: Pakai Java 17 (Standar baru Android Studio)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- Core & Activity ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")

    // --- Jetpack Compose (Versi Master) ---
    // Menggunakan hardcoded version sesuai Master supaya library-nya konsisten
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Icon Extended
    implementation("androidx.compose.material:material-icons-extended")

    // --- Navigation & VM ---
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2")

    // --- Data & Networking (Fitur Baru Login/Register butuh ini) ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // --- Local Storage (WAJIB ADA untuk simpan Token) ---
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // --- UI Libs & Image Loading ---
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("io.coil-kt:coil-compose:2.6.0")

    // --- Testing ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}