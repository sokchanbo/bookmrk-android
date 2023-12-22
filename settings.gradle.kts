@file:Suppress("UnstableApiUsage")

include(":feature:editgroup")


pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Bookmrk"
include(":app")

include(":core:common")
include(":core:designsystem")
include(":core:ui")
include(":core:database")
include(":core:model")
include(":core:data")

include(":feature:home")
include(":feature:addbookmark")
include(":feature:addeditcollection")
include(":feature:bookmarks")
