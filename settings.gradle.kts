// settings.gradle.kts

pluginManagement {
    repositories {
        // Hapus blok 'content {}' yang membatasi pencarian plugin
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ALP_VP_FRONTEND"
include(":app")