pluginManagement {
    val androidGradlePluginVersion: String by settings
    plugins {
        id("com.android.library") version androidGradlePluginVersion
    }
    repositories {
        gradlePluginPortal()
        google()
    }
}

// Include package dependencies
include(":react-native-reanimated")
project(":react-native-reanimated").projectDir = File(rootProject.projectDir, "react-native-reanimated/android-npm")

include(":react-native-reanimated-source")
project(":react-native-reanimated-source").projectDir = File(rootProject.projectDir, "react-native-reanimated/android")
