plugins {
    alias(libs.plugins.bookmrk.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.cb.bookmrk.core.domain"
}

dependencies {
    api(project(":core:data"))
    api(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.javax.inject)
}