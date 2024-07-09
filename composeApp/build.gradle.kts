import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        val exposedVersion = "0.52.0"
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

            implementation("com.patrykandpatrick.vico:compose:2.0.0-alpha.22")
            implementation("com.patrykandpatrick.vico:core:2.0.0-alpha.22")
            implementation("com.patrykandpatrick.vico:compose-m3:2.0.0-alpha.22")

            implementation("org.xerial:sqlite-jdbc:3.46.0.0")

            implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

            // https://mvnrepository.com/artifact/org.mindrot/jbcrypt
            implementation("org.mindrot:jbcrypt:0.4")

            // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
            implementation("org.slf4j:slf4j-simple:2.0.13")

            implementation("io.github.sunny-chung:composable-table:1.2.0")

            //implementation("dev.gitlive:firebase-auth:1.13.0")
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "spark4.pwetty"
            packageVersion = "1.0.0"
        }
    }
}
