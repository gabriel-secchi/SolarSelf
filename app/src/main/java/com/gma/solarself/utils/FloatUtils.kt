package com.gma.solarself.utils

fun Float.toEnergyCharge(): String {
    return this.toStringCharge(WATT_HOUR, KILOWATT_HOUR)
}

fun Float.toPowerCharge(): String {
    return this.toStringCharge(WATT, KILOWATT)
}

private fun Float.toStringCharge(wattMask: String, kilowattMask: String): String {
    if(this < 1) {
        val floatParts = this.toString().trim().split(".")
        return floatParts.last().plus(" $wattMask")
    }

    return this.toString().plus(" $kilowattMask")
}