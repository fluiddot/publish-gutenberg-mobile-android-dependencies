pluginManagement {
    plugins {
        id("com.android.library") version "4.2.2"
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