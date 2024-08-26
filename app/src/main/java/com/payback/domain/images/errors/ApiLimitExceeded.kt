package com.payback.domain.images.errors

import kotlin.time.Duration

class ApiLimitExceeded(val resetDelay: Duration) : Throwable("API rate limit exceeded")