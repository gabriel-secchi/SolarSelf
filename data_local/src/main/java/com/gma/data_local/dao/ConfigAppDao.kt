package com.gma.data_local.dao

interface ConfigAppDao {
    fun getConfig(Key: String): String?
    fun saveConfig(Key: String, value: String)
    fun removeConfig(Key: String)
}