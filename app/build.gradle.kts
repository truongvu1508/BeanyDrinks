plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.beanydrinks"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.beanydrinks"
        minSdk = 26
        targetSdk = 34
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
<<<<<<< HEAD

    buildFeatures {
        viewBinding = true
    }

=======
>>>>>>> 472eb9fc03cd2230b9ca287b8e7b49b5112073e0
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
<<<<<<< HEAD
=======

>>>>>>> 472eb9fc03cd2230b9ca287b8e7b49b5112073e0
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
<<<<<<< HEAD
    implementation("me.relex:circleindicator:2.1.6")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
=======
>>>>>>> 472eb9fc03cd2230b9ca287b8e7b49b5112073e0
}