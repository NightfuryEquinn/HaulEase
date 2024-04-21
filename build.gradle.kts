buildscript {
  dependencies {
    classpath(libs.google.services)
  }
}

plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.jetbrainsKotlinAndroid) apply false
  id("com.google.gms.google-services") version "4.4.1" apply false
}