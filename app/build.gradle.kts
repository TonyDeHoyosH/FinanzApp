plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    Firebase
    id("com.google.gms.google-services")
//    Hilt
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.antonioselvas.finanzasapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.antonioselvas.finanzasapp"
        minSdk = 29
        targetSdk = 36
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


//    My dependecies
//    Navigation
    implementation(libs.androidx.navigation.compose)

//    Extend icons
    implementation(libs.androidx.material.icons.extended)

//    Data store
    implementation(libs.androidx.datastore.preferences)

//    Lottie
    implementation(libs.lottie.compose.v671)

//    Firebase
    implementation(libs.firebase.analytics)
    implementation(platform(libs.firebase.bom))

//    FirebaseAuth
    implementation(libs.firebase.auth)

//    FireStore
    implementation(libs.firebase.firestore)

//    PasswordInput
    implementation(libs.androidx.compose.bom.v20251101)
    implementation(libs.material3)
    implementation (libs.androidx.material.icons.extended)

//    Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

}
