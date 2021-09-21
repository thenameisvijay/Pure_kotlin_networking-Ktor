import com.android.tools.build.jetifier.core.utils.Log.e

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

    val baseUrl = project.property("BASE_URL") as String
    val subUrlHead = project.property("SUB_URL_HEAD") as String

    defaultConfig {
        applicationId = "com.vj.pure_kotlin_networking_ktor"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        fun com.android.build.api.dsl.BaseFlavor.buildConfigString(name: String, value: String) =
            buildConfigField("String", name, "\"$value\"")

        buildConfigString("BASE_URL", baseUrl)
        buildConfigString("SUB_URL_HEAD", subUrlHead)

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
    val hiltVersion = "2.38.1"
    val ktorVersion = "1.5.0"

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // android ktx
    implementation ("androidx.activity:activity-ktx:1.3.1")

    //Ktor
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")

    // ViewModel
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

