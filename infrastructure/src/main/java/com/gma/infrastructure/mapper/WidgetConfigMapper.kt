package com.gma.infrastructure.mapper

import com.gma.infrastructure.model.WidgetConfig
import com.gma.data_local.model.WidgetConfig as WidgetConfigDaoModel

fun WidgetConfigDaoModel.toWidgetConfigModel() =
    WidgetConfig(
        monitoredStationId = this.monitoredStationId
    )

