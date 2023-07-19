package com.gma.data_remote.di

import com.gma.data_remote.dataSource.UserStationDataSource
import com.gma.data_remote.dataSource.ValidateConnectionDataSource
import com.gma.data_remote.dataSourceImplementation.UserStationDataSourceImpl
import com.gma.data_remote.dataSourceImplementation.ValidateConnectionDataSourceImpl
import com.gma.data_remote.network.ApiDataEngine
import com.gma.data_remote.network.ApiDataEngineImpl
import com.gma.data_remote.network.NetworkEngine
import com.gma.data_remote.network.NetworkEngineImpl
import com.gma.data_remote.network.buildApiService
import com.gma.data_remote.network.interceptors.HeadersInterceptor
import com.gma.data_remote.network.okHttpClientBuilder
import com.google.gson.Gson
import okhttp3.Interceptor
import org.koin.dsl.bind
import org.koin.dsl.module

val dataRemoteModule = module {

    single { buildApiService(get()) }
    single { okHttpClientBuilder(getAll()) }
    single { HeadersInterceptor(get()) } bind Interceptor::class

    single<ApiDataEngine> {
        ApiDataEngineImpl()
    }

    single<NetworkEngine> {
        NetworkEngineImpl(
            apiDataAccess = get(),
            apiService = buildApiService(get()),
            gson = Gson()
        )
    }

    factory<UserStationDataSource> {
        UserStationDataSourceImpl(
            networkEngine = get()
        )
    }

    factory<ValidateConnectionDataSource> {
        ValidateConnectionDataSourceImpl(
            apiDataAccess = get(),
            networkEngine = get()
        )
    }

}