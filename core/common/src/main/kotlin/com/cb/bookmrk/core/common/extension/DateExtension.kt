package com.cb.bookmrk.core.common.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatAsString(pattern: String = "dd MMM yyyy")=
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)
