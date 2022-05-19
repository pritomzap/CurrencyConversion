import AttachDependencies.implementCoroutine
import AttachDependencies.implementLifeCycle
import AttachDependencies.implementRetrofit
import AttachDependencies.implementAndroidX
import AttachDependencies.implementHilt
import AttachDependencies.implementOthers

plugins {
    id ("com.android.application")
    id ("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs")
    id("kotlin-android")
}


android {
    compileSdkVersion (RootConfig.compileSkdVersion)
    buildToolsVersion (RootConfig.buildToolsVersion)

    defaultConfig {
        applicationId  = AppConfig.applicationId
        minSdkVersion (RootConfig.minSdkVersion)
        targetSdkVersion (RootConfig.targetSdkVersion)

        versionCode (AppConfig.versionCode)
        versionName (AppConfig.versionName)

        renderscriptTargetApi = RootConfig.renderscriptTargetApi
        renderscriptSupportModeEnabled (RootConfig.renderscriptSupportModeEnabled)

        testInstrumentationRunner (RootConfig.testInstrumentationRunner)

        multiDexEnabled = RootConfig.multiDexEnabled
        vectorDrawables.useSupportLibrary = RootConfig.vectorDrawablesUseSupportLibrary

        applicationVariants.all {
            val variant = this
            variant.outputs
                .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                .forEach { output ->
                    val outputFileName = "${AppConfig.appVarientName}_(${variant.baseName}_${variant.versionName} ${variant.versionCode}).apk"
                    output.outputFileName = outputFileName
                }
        }
    }

    buildFeatures {
        dataBinding = true
    }

    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError  = false
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions{
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude ("**/attach_hotspot_windows.dll")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/licenses/ASM")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled  = false
            isShrinkResources = false
            isDebuggable  = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release"){
            isMinifyEnabled  = true
            isShrinkResources  = true
            isDebuggable  = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions (AppConfig.productFlavor)
    productFlavors{
        create("dev"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/\"")
            buildConfigField("String", "API_KEY", "\"6GjnCdg3Uc7dvvMY5CWfIm1kNxwc9Mp9\"")
        }
        create("staging"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/\"")
            buildConfigField("String", "API_KEY", "\"6GjnCdg3Uc7dvvMY5CWfIm1kNxwc9Mp9\"")
        }
        create("production"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/\"")
            buildConfigField("String", "API_KEY", "\"6GjnCdg3Uc7dvvMY5CWfIm1kNxwc9Mp9\"")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementRetrofit()
    implementHilt()
    implementAndroidX()
    implementCoroutine()
    implementLifeCycle()
    implementOthers()
}
kapt {
    correctErrorTypes = true
}