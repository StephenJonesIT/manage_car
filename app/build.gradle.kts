plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "vn.edu.stu.thanhsang.managecar"
    compileSdk = 34

    defaultConfig {
        applicationId = "vn.edu.stu.thanhsang.managecar"
        minSdk = 28
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures.viewBinding = true
}

dependencies {
    implementation (libs.hdodenhof.circleimageview)
    implementation (libs.play.services.maps)
    implementation (libs.play.services.location)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}