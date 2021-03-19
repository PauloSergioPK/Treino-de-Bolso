import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
}


android {
    android.buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileSdkVersion(30)
    buildToolsVersion ("30.0.3")

    defaultConfig {
        applicationId = "com.karasdecc.treinodebolso"
        minSdkVersion (19)
        targetSdkVersion (30)
        versionCode = 1
        versionName =  "0.0.1"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(platform("com.google.firebase:firebase-bom:26.7.0"))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.9")

    // Android Support
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation("androidx.mediarouter:mediarouter:1.2.2")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.preference:preference:1.1.1")

    // Firebase
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-core")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-inappmessaging-display")

    // Play services
    implementation("com.google.android.gms:play-services-analytics:17.0.0")
    implementation("com.google.android.gms:play-services-gcm:17.0.0")
    implementation("com.google.android.play:core-ktx:1.8.1")
    implementation("com.google.android.gms:play-services-auth:19.0.0")

    // Others
    implementation ("com.google.android.material:material:1.3.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("org.jsoup:jsoup:1.13.1")

    kapt("com.github.bumptech.glide:compiler:4.11.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

}