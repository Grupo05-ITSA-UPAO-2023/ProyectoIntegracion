plugins {
<<<<<<< HEAD
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
=======
<<<<<<< HEAD
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
=======
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
>>>>>>> aa1cd682c895b13a9c3658412a960855e41dec28
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
    id("com.google.gms.google-services")
}

android {
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
    namespace = "com.example.proyectointegracion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectointegracion"
        minSdk = 22
        targetSdk = 33
<<<<<<< HEAD
=======
=======
    namespace = "com.example.proyecto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyecto"
        minSdk = 21
        targetSdk = 34
>>>>>>> aa1cd682c895b13a9c3658412a960855e41dec28
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

<<<<<<< HEAD

=======
<<<<<<< HEAD

=======
>>>>>>> aa1cd682c895b13a9c3658412a960855e41dec28
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
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
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
    viewBinding {
        enable = true
    }
    buildFeatures{
        viewBinding = true
    }
    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
<<<<<<< HEAD
=======
=======
>>>>>>> aa1cd682c895b13a9c3658412a960855e41dec28
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    buildFeatures{
        viewBinding = true
    }
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation ("com.android.volley:volley:1.2.1")
<<<<<<< HEAD
=======
=======
}



dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
>>>>>>> aa1cd682c895b13a9c3658412a960855e41dec28
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85

    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.android.gms:play-services-auth:20.0.0")
    implementation("com.stripe:stripe-android:20.35.1")
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
<<<<<<< HEAD
=======
=======
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
>>>>>>> aa1cd682c895b13a9c3658412a960855e41dec28
>>>>>>> ef4fb91dc62322468d1c57af353d8dadb494ff85
}