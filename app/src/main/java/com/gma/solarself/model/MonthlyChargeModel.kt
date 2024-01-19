package com.gma.solarself.model

import com.gma.infrastructure.model.StationMonthDataModel

data class MonthlyChargeModel(
    val total: Int,
    val average: Double,
    val measureType: String,
    val monthlyData: List<StationMonthDataModel>
)
