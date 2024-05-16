plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  id("com.google.gms.google-services")
}

android {
  namespace = "com.example.haulease"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.haulease"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
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
  implementation(libs.ui)
  // implementation(libs.androidx.material3) - Avoid overloading functions
  implementation(libs.androidx.navigation.common.ktx)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.material3.android)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  // Icons
  implementation(libs.androidx.material.icons.extended)

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.converter.gson)

  // Coil
  implementation(libs.coil)

  // Firebase Auth & Storage
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.auth)
  implementation(libs.firebase.storage)

  // Google Maps & Location Services
  implementation(libs.maps.compose)
  implementation(libs.play.services.maps)
  implementation(libs.play.services.location)

  // Accompanist Remember Permissions
  implementation(libs.accompanist.permissions)

  // For Compose Dialogs
  implementation(libs.core)
  implementation(libs.date.time)
  implementation(libs.list)

  // For View Model
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)
}