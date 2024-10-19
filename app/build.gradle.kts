plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.reminderpill"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.reminderpill"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.cast.framework)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //circle imageView

    implementation("de.hdodenhof:circleimageview:3.1.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.12.0")


    //sdp
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    // ssp
    implementation("com.intuit.ssp:ssp-android:1.1.1")

    //viewpager
    implementation("androidx.viewpager2:viewpager2:1.1.0")

//material text
    implementation("com.google.android.material:material:1.3.0-alpha02")

//material btn

    implementation("com.google.android.material:material:1.4.0")

    //countryPicker
    implementation ( "com.hbb20:ccp:2.5.0")

    // otpView
    implementation ("io.github.chaosleung:pinview:1.4.4")
    implementation ("com.github.myDario:DarioWeekViewDatePicker:1.0.3")

    implementation ("com.google.android.material:material:1.4.0")




    // firebase
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth:19.4.0")
    implementation("com.google.android.gms:play-services-auth:18.1.0")

    implementation("com.google.firebase:firebase-database")

    implementation("com.google.android.gms:play-services-auth:21.1.0")
    //FireStore
    implementation("com.google.firebase:firebase-firestore:22.0.1")

}


