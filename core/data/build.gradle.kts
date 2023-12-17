plugins {
    alias(libs.plugins.bookmrk.android.library)
    alias(libs.plugins.bookmrk.android.hilt)
}

android {
    namespace = "com.cb.bookmrk.core.data"
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:model"))
}
