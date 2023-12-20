plugins {
    alias(libs.plugins.bookmrk.android.application)
    alias(libs.plugins.bookmrk.android.application.compose)
    alias(libs.plugins.bookmrk.android.hilt)
}

android {
    namespace = "com.cb.bookmrk"

    defaultConfig {
        applicationId = "com.cb.bookmrk"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":feature:home"))
    implementation(project(":feature:addeditcollection"))
    implementation(project(":feature:addbookmark"))
    implementation(project(":feature:bookmarks"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
}