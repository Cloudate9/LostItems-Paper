import kr.entree.spigradle.kotlin.*

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("kr.entree.spigradle") version "2.4.2"
    kotlin("jvm") version "1.6.21"
}

group = "com.cloudate9.lostitems"
version = "1.0.1"


repositories {
    codemc()
    mavenCentral()
    papermc()
    jitpack()
}

dependencies {
    api(bStats("3.0.0"))
    api("io.insert-koin:koin-core:3.2.0")
    compileOnly(paper("1.18.2"))
    testApi(kotlin("test"))
}

tasks {
    compileJava {
        options.release.set(17)
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    compileTestJava {
        options.release.set(17)
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }


    prepareSpigotPlugins {
        setDependsOn(mutableListOf(shadowJar))
    }

    runSpigot {
        jvmArgs = mutableListOf(
            "-Xmx2G",
            "-Xms2G",
            "-XX:+UseZGC",
            "-XX:+ZUncommit",
            "-XX:ZUncommitDelay=3600",
            "-XX:+ZProactive",
            "-XX:+AlwaysPreTouch",
            "-XX:+DisableExplicitGC",
        )

        setDependsOn(mutableListOf(acceptSpigotEula, configSpigot, prepareSpigotPlugins))
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")

        relocate("org.bstats", "${rootProject.group}.dependencies.bstats")
        relocate("org.koin", "${rootProject.group}.dependencies.koin")
    }
}

spigot {
    authors = listOf("Cloudate9")
    apiVersion = "1.18"
    description = "Feeling up for a challenge? Lose a random inventory item when you take damage."
    website = "https://github.com/cloudate9/lostitems"
    commands {
        create("lostitems") {
            aliases = listOf("li")
            description = "The main lost items command"
            usage = "/lostitems"
        }
    }
    permissions {
        create("lostitems.configure") {
            description = "If one can configure lost items"
            defaults = "op"
        }
    }
    permissions {
        create("lostitems.updater") {
            description = "If one can view update messages for this plugin"
            defaults = "op"
        }
    }
    excludeLibraries = listOf("*")
}