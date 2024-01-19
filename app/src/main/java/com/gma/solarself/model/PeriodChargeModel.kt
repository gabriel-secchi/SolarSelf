package com.gma.solarself.model

data class PeriodChargeModel(
    val total: Int,
    val average: Double,
    val measureType: String,
    val listEnergy: List<ChartDataModel>
)
