plugins {
    alias(libs.plugins.android.application)
}

android {
    // Namespace check karein
    namespace = "com.example.exp_6_gps_location_app"

    // API 36 set karna zaroori hai error hatane ke liye
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.exp_6_gps_location_app"
        minSdk = 24
        targetSdk = 36 // Ise bhi 36 kar dein
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Fast Location fetch karne ke liye library
    implementation("com.google.android.gms:play-services-location:21.0.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}