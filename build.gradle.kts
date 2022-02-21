import org.json.JSONObject

val defaultCompileSdkVersion = 30
val defaultMinSdkVersion = 21
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

allprojects {
    // group = "com.github.wordpress-mobile"
    group = "com.github.fluiddot"
}

plugins {
    id("com.android.library") apply false
    id("maven-publish")
}

subprojects {
    afterEvaluate {
        apply { plugin("maven-publish") }
        allprojects {
            repositories {
                maven {
                    setUrl("https://a8c-libs.s3.amazonaws.com/android/react-native-mirror")
                }
                google()
            }
        }
        afterEvaluate {
            publishing {
                publications {
                    create<MavenPublication>(name) {
                        try {
                            from(components.get("release"))
                        } catch( e: Exception ) {
                            println("'$name' - Release build variant not defined, trying to use default artifact.")

                            val defaultArtifacts = configurations.getByName("default").artifacts
                            if(defaultArtifacts.isEmpty()) {
                                throw Exception("'$name' - No default artifact found, aborting publishing!")
                            }
                            val defaultArtifact = defaultArtifacts.getFiles().getSingleFile()
                            artifact(defaultArtifact)
                        }
                    }
                }
            }
        }
    }
}