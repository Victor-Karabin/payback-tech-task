package com.payback.ui.images.list.models

import androidx.compose.runtime.Stable
import java.time.Duration

@Stable
internal data class ApiLimitExceeded(val resetDelay: Duration)