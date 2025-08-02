// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Google Services
    alias(libs.plugins.google.gms.google.services) apply false

    // Hilt
    alias(libs.plugins.hilt.android) apply false

    // KSP
    alias(libs.plugins.ksp) apply false
}

buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
