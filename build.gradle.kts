buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
    }
    repositories {
        google()
        mavenCentral()

    }
}
allprojects {
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                // Avoid having to stutter experimental annotations all over the codebase
                "-opt-in=androidx.compose.ui.text.ExperimentalTextApi",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.InternalCoroutinesApi",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=kotlinx.coroutines.DelicateCoroutinesApi",
                "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
                "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.paging.ExperimentalPagingApi",
                "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
                "-opt-in=androidx.compose.runtime.InternalComposeApi",
                "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi",
            )
        }
    }
    repositories {
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {

            }
            credentials {
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = "mapbox"
                // Use the secret token you stored in gradle.properties as the password
                password = "sk.eyJ1IjoidGhla2FhaWxhc2hzaGFybWEiLCJhIjoiY2xtNnBxNmFxMG1qeTNkbW1oazhjMHhreSJ9.3AKIgPKr9iqO8JeCTDtbDw"
            }
        }
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
}
true // Needed to make the Suppress annotation work for the plugins block