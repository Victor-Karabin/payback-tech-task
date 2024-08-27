package com.payback.data.rest

internal class RestThrowable(
    val code: Int,
    val url: String,
    val body: String?,
    val headers: Map<String, String>
) : Throwable()
