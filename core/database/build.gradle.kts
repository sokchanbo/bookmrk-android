plugins {
    alias(libs.plugins.bookmrk.android.library)
    alias(libs.plugins.bookmrk.android.hilt)
    alias(libs.plugins.bookmrk.android.room)
}

android {
    namespace = "com.cb.bookmrk.core.database"
}

dependencies {
    implementation(project(":core:model"))
}
