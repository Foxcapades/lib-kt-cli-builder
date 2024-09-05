import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  kotlin("jvm") version "2.0.20"
  id("org.jetbrains.dokka") version "1.9.20"
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "io.foxcapades.kt"
version = "0.3.0"

val featureVersion = (version as String).let { it.substring(0, it.lastIndexOf('.') + 1) + "0" }

repositories {
  mavenCentral()
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(21)
    vendor = JvmVendorSpec.AMAZON
  }

  java {
    withSourcesJar()
    withJavadocJar()
  }
}

dependencies {
  implementation(kotlin("reflect"))

  testImplementation("org.mockito:mockito-junit-jupiter:5.13.0")
  testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
  testImplementation("org.mockito:mockito-core:5.13.0")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events(
      TestLogEvent.PASSED,
      TestLogEvent.FAILED,
      TestLogEvent.SKIPPED,
    )
  }
}

tasks.dokkaHtml {
  val targetDir = file("docs/dokka/$featureVersion")

  outputDirectory = targetDir

  doFirst {
    targetDir.deleteRecursively()
  }
}

nexusPublishing {
  repositories {
    sonatype {
      nexusUrl = uri("https://s01.oss.sonatype.org/service/local/")
      snapshotRepositoryUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
      username = project.findProperty("nexus.user") as String? ?: System.getenv("NEXUS_USER")
      password = project.findProperty("nexus.pass") as String? ?: System.getenv("NEXUS_PASS")
    }
  }
}

publishing {
  publications {
    create<MavenPublication>("gpr") {
      from(components["java"])
      pom {
        name.set("Kotlin CLI Call Builder")
        description.set("Provides delegates, annotations, and tools to generate CLI calls from " +
          "classes, making it easier to define type-safe, validating wrappers around " +
          "external CLI tools.")
        url.set("https://github.com/foxcapades/lib-kt-cli-builder")

        licenses {
          license {
            name.set("MIT")
          }
        }

        developers {
          developer {
            id.set("epharper")
            name.set("Elizabeth Paige Harper")
            email.set("foxcapades.io@gmail.com")
            url.set("https://github.com/foxcapades")
          }
        }

        scm {
          connection.set("scm:git:git://github.com/foxcapades/lib-kt-cli-builder.git")
          developerConnection.set("scm:git:ssh://github.com/foxcapades/lib-kt-cli-builder.git")
          url.set("https://github.com/foxcapades/lib-kt-cli-builder")
        }
      }
    }
  }
}

signing {
  useGpgCmd()

  sign(configurations.archives.get())
  sign(publishing.publications["gpr"])
}

tasks.create("update-readme") {
  doLast {
    val tmp    = file("readme.adoc.tmp")
    val readme = file("readme.adoc")

    tmp.delete()
    tmp.bufferedWriter().use { w ->
      readme.bufferedReader().use { r ->
        r.lineSequence()
          .map {
            when {
              !it.startsWith(':')                -> it
              it.startsWith(":version-actual:")  -> ":version-actual: $version"
              it.startsWith(":version-feature:") -> ":version-feature: $featureVersion"
              else                               -> it
            }
          }
          .forEach {
            w.write(it)
            w.newLine()
          }
      }
    }

    readme.delete()
    tmp.renameTo(readme)
  }
}
