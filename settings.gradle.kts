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
include(":react-native-gesture-handler")
project(":react-native-gesture-handler").projectDir = File(rootProject.projectDir, "node_modules/react-native-gesture-handler/android")

include(":react-native-reanimated")
project(":react-native-reanimated").projectDir = File(rootProject.projectDir, "node_modules/react-native-reanimated/android")