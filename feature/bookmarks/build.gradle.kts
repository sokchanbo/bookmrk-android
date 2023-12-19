plugins {
    alias(libs.plugins.bookmrk.android.feature)
    alias(libs.plugins.bookmrk.android.library.compose)
}

android {
    namespace = "com.cb.bookmrk.feature.bookmarks"
}

dependencies {
    implementation(libs.coil.kt.compose)
}