package com.gma.data_local.dao

import com.gma.data_local.model.DataAccess

interface ConfigApiDao {
    fun saveDataAccess(dataAccess: DataAccess)
    fun getDataAccess(): DataAccess?
}