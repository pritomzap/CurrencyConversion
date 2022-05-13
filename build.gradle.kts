buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.2.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath ("com.google.gms:google-services:4.3.8")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath ("io.realm:realm-gradle-plugin:10.6.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url  = uri("https://www.jitpack.io") }
        //  maven { url 'https://mapbox.bintray.com/mapbox' }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}