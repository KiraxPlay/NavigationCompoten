plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id ("kotlin-kapt")
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.navigationcomponentexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.navigationcomponentexample"
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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    val navVersion = "2.7.0"
    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")//iniciando una varible ya que se llamara una libreria
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    val camVersion  = "1.2.2"
    implementation("androidx.camera:camera-camera2:$camVersion")
    implementation("androidx.camera:camera-lifecycle:$camVersion")
    implementation("androidx.camera:camera-view:$camVersion")

    // Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}