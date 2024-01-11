package com.gma.solarself.customExceptions

class NoPeriodConfigured: Exception() {
    override val message: String
        get() = "no period configured"
}