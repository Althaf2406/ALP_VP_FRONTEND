// build.gradle.kts (Project Level)

plugins {
    // Plugin Android dan Kotlin (Gunakan versi yang disarankan oleh Android Studio)
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false

    // TIDAK ADA PLUGIN HILT ATAU KSP LAGI DI SINI

    // Jika Anda menggunakan alias di file lain, deklarasikan di sini
    alias(libs.plugins.kotlin.compose) apply false
}