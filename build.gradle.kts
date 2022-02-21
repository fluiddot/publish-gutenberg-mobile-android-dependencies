import org.json.JSONObject

val defaultCompileSdkVersion = 30
val defaultMinSdkVersion = 30
val defaultTargetSdkVersion = 30

// Set project extra properties
project.ext.set("compileSdkVersion", defaultCompileSdkVersion)
project.ext.set("minSdkVersion", defaultMinSdkVersion)
project.ext.set("targetSdkVersion", defaultTargetSdkVersion)
project.ext.set("react", extra( mapOf(
    "enableHermes" to true
)))

// Fetch dependencies versions from package.json
val packageJson = JSONObject(File("package.json").readText())
val packageDependencies = packageJson.optJSONObject("dependencies")
val packageDevDependencies = packageJson.optJSONObject("devDependencies")
val rnVersion = packageDevDependencies.optString("react-native")

plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    compileSdkVersion(defaultCompileSdkVersion)
}

allprojects {
    // group = "com.github.wordpress-mobile"
    group = "com.github.fluiddot"
}

subprojects {
    afterEvaluate {
        if (name == "react-native-gesture-handler") {
            apply { plugin("maven-publish") }
            allprojects {
                repositories {
                    maven {
                       setUrl("https://a8c-libs.s3.amazonaws.com/android/react-native-mirror")
                    }
                    google()
                }
            }
            configurations.all {
                resolutionStrategy {
                    force("com.facebook.react:react-native:+", "com.facebook.react:react-native:0.66.2")
                }
            }
            afterEvaluate {
                publishing {
                    publications {
                        create<MavenPublication>(name) {
                            from(components.get("release"))
                        }
                    }
                }
            }
        }
        if (name == "react-native-reanimated") {
            apply { plugin("maven-publish") }
            allprojects {
                repositories {
                    maven {
                       setUrl("https://a8c-libs.s3.amazonaws.com/android/react-native-mirror")
                    }
                    google()
                }
            }
            configurations.all {
                resolutionStrategy {
                    force("com.facebook.react:react-native:+", "com.facebook.react:react-native:0.66.2")
                }
            }
            afterEvaluate {
                publishing {
                    publications {
                        create<MavenPublication>(name) {
                            artifact(configurations.default.artifacts.getFiles().getSingleFile())
                        }
                    }
                }
            }
        }
    }
}