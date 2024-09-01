plugins {
  kotlin("jvm") version "2.0.20"
  id("org.jetbrains.dokka") version "1.9.20"
}

repositories {
  mavenCentral()
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(8)
    vendor = JvmVendorSpec.AMAZON
  }

  java {
    withSourcesJar()
    withJavadocJar()
  }
}

dependencies {
  implementation(kotlin("reflect"))
}