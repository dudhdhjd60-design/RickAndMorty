plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.sqldelight)
    kotlin("kapt")
}

android {
    namespace = "com.example.rickandmorty.core.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

sqldelight {
    databases {
        create("RickAndMortyDatabase") {
            packageName.set("com.example.rickandmorty.core.database")
            srcDirs.setFrom("src/main/sqldelight")
        }
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines)
    implementation(libs.coroutines.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
