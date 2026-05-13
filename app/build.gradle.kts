plugins {

    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)
}

android {

    namespace = "com.sip"

    compileSdk = 36

    defaultConfig {

        applicationId = "com.sip"

        minSdk = 29
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }


    buildFeatures {

        compose = true
    }
}

dependencies {

    /*
    ---------------------------------------------------
    CORE
    ---------------------------------------------------
    */

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)

    /*
    ---------------------------------------------------
    COMPOSE
    ---------------------------------------------------
    */

    implementation(
        platform(
            libs.androidx.compose.bom
        )
    )

    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.compose.ui.graphics)

    implementation(
        libs.androidx.compose.ui.tooling.preview
    )

    implementation(
        libs.androidx.compose.material3
    )

    implementation(
        libs.androidx.compose.material.icons.extended
    )

    implementation(
        libs.androidx.lifecycle.viewmodel.compose
    )

    /*
    ---------------------------------------------------
    SPLASH SCREEN
    ---------------------------------------------------
    */

    implementation(
        libs.androidx.core.splashscreen
    )

    /*
    ---------------------------------------------------
    ROOM
    ---------------------------------------------------
    */

    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.room.ktx)

    ksp(libs.androidx.room.compiler)

    /*
    ---------------------------------------------------
    DATASTORE
    ---------------------------------------------------
    */

    implementation(
        libs.androidx.datastore.preferences
    )

    /*
    ---------------------------------------------------
    WORK MANAGER
    ---------------------------------------------------
    */

    implementation(
        libs.androidx.work.runtime.ktx
    )

    /*
    ---------------------------------------------------
    TEST
    ---------------------------------------------------
    */

    testImplementation(libs.junit)

    androidTestImplementation(
        libs.androidx.junit
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        platform(
            libs.androidx.compose.bom
        )
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )
}