package com.cb.bookmrk.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val bookmrkDispatcher: BookmrkDispatchers)

enum class BookmrkDispatchers {
    Default,
    IO,
}
