plugins {
    id("com.android.application")
    id("kotlin-android")
    id ("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.vj.pure_kotlin_networking_ktor"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        val BASE_URL = "BASE_URL"
        buildConfigField("String", "BASE_URL", BASE_URL)

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    val lifecycleVersion = "2.4.0-alpha02"
    val retrofitVersion = "2.9.0"
    val hiltVersion = "2.38.1"
    val navVersion = "2.3.5"
    val ktorVersion = "1.5.0"

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    //Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Ktor
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation( "io.ktor:ktor-client-serialization:$ktorVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")

    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("android.arch.lifecycle:extensions:1.1.1")

    //Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.71828")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

