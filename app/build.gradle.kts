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

        multiDexEnabled = RootConfig.multiDexEnabled
        vectorDrawables.useSupportLibrary = RootConfig.vectorDrawablesUseSupportLibrary
        testInstrumentationRunner (RootConfig.testInstrumentationRunner)

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
        exclude("META-INF/*.kotlin_module")
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

    flavorDimensions ("environment")
    productFlavors{
        create("dev"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/\"")
            buildConfigField("String", "API_KEY", "\"InK4FtnyGNGABNTVlMxz7cwCxAKNU1cU\"")
        }
        create("staging"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/\"")
            buildConfigField("String", "API_KEY", "\"InK4FtnyGNGABNTVlMxz7cwCxAKNU1cU\"")
        }
        create("production"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/\"")
            buildConfigField("String", "API_KEY", "\"InK4FtnyGNGABNTVlMxz7cwCxAKNU1cU\"")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}")

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.core:core-ktx:1.7.0")

    implementation ("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt ("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.6.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.facebook.stetho:stetho-okhttp3:1.5.1")


    implementation ("com.google.code.gson:gson:2.8.7")


    val coroutine_version = "1.5.1"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutine_version}")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutine_version}")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${coroutine_version}")

    // ViewModel
    val lifeCycleVersion = "2.2.0"
    implementation ("androidx.lifecycle:lifecycle-extensions:$lifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion")

    val activity_version = "1.3.0"
    implementation ("androidx.activity:activity-ktx:$activity_version")
    val fragment_version = "1.3.6"
    implementation ("androidx.fragment:fragment-ktx:$fragment_version")
    val nav_version_ktx = "2.3.5"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version_ktx")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version_ktx")


    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}