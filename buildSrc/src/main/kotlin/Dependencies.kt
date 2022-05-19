import org.gradle.api.artifacts.dsl.DependencyHandler


object Versions {
    val legacySupport = "1.0.0"
    val constraintLayout = "2.1.3"
    val appCompatVersion = "1.4.1"
    val retrofitOkhttpInterceptor = "4.9.1"
    val retrofitConverter = "2.6.0"
    val core_ktx = "1.7.0"
    val kotlinVersion = "1.5.30"
    val hiltVersion = "2.38.1"
    val coroutineVersion = "1.5.1"
    val lifeCycleVersion = "2.2.0"
    val activity_version = "1.3.0"
    val retrofit = "2.9.0"
    val fragment_version = "1.3.6"
    val nav_version_ktx = "2.3.5"
    val arch_version = "2.1.0"
}

object Implementations{
    val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineVersion}"
    val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"
    val coroutinePlayService= "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutineVersion}"

    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleVersion}"
    val lifecycleruntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleVersion}"
    val lifecycleviewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleVersion}"
    val lifecyclelivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleVersion}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofitConverter}"
    val retrofitOkhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitOkhttpInterceptor}"

    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val ktxCore = "androidx.core:core-ktx:${Versions.core_ktx}"
    val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    val activity = "androidx.activity:activity-ktx:${Versions.activity_version}"
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment_version}"
    val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version_ktx}"
    val navUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version_ktx}"

    val hiltAndroid =  "com.google.dagger:hilt-android:${Versions.hiltVersion}"

    val kotlinStdlib =  "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    val gson = "com.google.code.gson:gson:2.8.7"
    val googleMaterial = "com.google.android.material:material:1.6.0"
    val googleArCore = "com.google.ar:core:1.31.0"
}

object AndroidTestImplementations{
    val coroutineAndroidTest= "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineVersion}"

    val jUnit = "androidx.test.ext:junit:1.1.3"
    val espresso = "androidx.test.espresso:espresso-core:3.4.0"

    val truth = "com.google.truth:truth:1.1"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.arch_version}"
    val testCoreKtx =  "androidx.test:core-ktx:1.4.0"

    val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"

}

object TestImplementations{
    val testRunner = "androidx.test:runner:1.4.0"
    val testJunit = "androidx.test.ext:junit:1.1.3"
    val truth = "com.google.truth:truth:1.1"
}

object KaptImplementation{
    val hiltKapt = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
}
object KaptTestImplementation{
    /*kaptAndroidTest*/
    val kaptTest = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
}

object AttachDependencies{
    fun DependencyHandler.implementCoroutine() {
        add("implementation", Implementations.coroutineCore)
        add("implementation", Implementations.coroutineAndroid)
        add("implementation", Implementations.coroutinePlayService)
        add("androidTestImplementation", AndroidTestImplementations.coroutineAndroidTest)
    }

    fun DependencyHandler.implementLifeCycle() {
        add("implementation", Implementations.lifecycleExtensions)
        add("implementation", Implementations.lifecycleruntime)
        add("implementation", Implementations.lifecycleviewmodel)
        add("implementation", Implementations.lifecyclelivedata)
    }
    fun DependencyHandler.implementRetrofit() {
        add("implementation", Implementations.retrofit)
        add("implementation", Implementations.retrofitGsonConverter)
        add("implementation", Implementations.retrofitConverter)
        add("implementation", Implementations.retrofitOkhttpInterceptor)
    }

    fun DependencyHandler.implementAndroidX() {
        add("implementation", Implementations.appCompat)
        add("implementation", Implementations.constrainLayout)
        add("implementation", Implementations.ktxCore)
        add("implementation", Implementations.legacySupport)
        add("implementation", Implementations.activity)
        add("implementation", Implementations.fragment)
        add("implementation", Implementations.navFragment)
        add("implementation", Implementations.navUi)

        add("androidTestImplementation", AndroidTestImplementations.jUnit)
        add("androidTestImplementation", AndroidTestImplementations.espresso)
        add("androidTestImplementation", AndroidTestImplementations.coreTesting)
        add("androidTestImplementation", AndroidTestImplementations.testCoreKtx)

        add("testImplementation", TestImplementations.testRunner)
        add("testImplementation", TestImplementations.testJunit)
    }

    fun DependencyHandler.implementHilt(){
        add("implementation", Implementations.hiltAndroid)
        add("androidTestImplementation", AndroidTestImplementations.hiltTest)

        add("kapt", KaptImplementation.hiltKapt)
        add("kaptAndroidTest", KaptTestImplementation.kaptTest)
    }

    fun DependencyHandler.implementOthers(){
        add("implementation", Implementations.kotlinStdlib)
        add("implementation", Implementations.gson)
        add("implementation", Implementations.googleMaterial)
        add("implementation", Implementations.googleArCore)
        add("androidTestImplementation", AndroidTestImplementations.truth)
        add("testImplementation", TestImplementations.truth)
    }
}

