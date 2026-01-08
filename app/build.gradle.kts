plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Menggunakan plugin Compose terbaru (sesuai HEAD kamu agar kompatibel)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.alp_vp_frontend"
    // Gunakan 35 (HEAD) jika kamu yakin tim mau upgrade,
    // TAPI kalau tim masih pake 34, ganti angka ini jadi 34.
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.alp_vp_frontend"
        minSdk = 24
        targetSdk = 35 // Samakan dengan compileSdk
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

    // --- BAGIAN INI PENTING (Punya Master) ---
    // Kita pakai Java 11 & Desugaring punya Althaf supaya support HP Android lama
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11" // Sesuaikan dengan Java 11 di atas
    }
    // -----------------------------------------

    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- PENTING: Core Library Desugaring (Punya Master) ---
    // Ini wajib ada karena compileOptions di atas mengaktifkan desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // --- Android & Compose (Punya Master - Pakai libs.***) ---
    // Menggunakan versi dari katalog tim agar seragam
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- Icons & Navigation (Gabungan) ---
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2") // Update ke versi stable terbaru

    // --- Network & Data (Punya Master + Implementasi Kamu) ---
    // Di Master sudah ada Retrofit & Coil, jadi fiturmu AMAN.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Pakai versi newer (4.12) punya kamu gpp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Datastore (Punya Kamu lebih baru 1.1.1, kita pakai itu)
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Image Loading (Coil)
    implementation("io.coil-kt:coil-compose:2.6.0") // Pakai versi 2.6.0 punya kamu biar support async image terbaru

    // --- Async ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}