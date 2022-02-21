import groovy.json.JsonSlurper

plugins {
    id("com.github.node-gradle.node") version "3.1.1"
}

node {
    download.set(true)
    version.set("12.18.3")
    nodeProjectDir.set(File("$projectDir/.."))

}