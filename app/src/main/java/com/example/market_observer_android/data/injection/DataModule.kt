package com.example.market_observer_android.data.injection

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.datastore.LocalDataStore
import com.example.market_observer_android.data.datastore.RemoteDataStore
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.data.repository.RepositoryImpl
import com.example.market_observer_android.data.rest.RestApi
import com.example.market_observer_android.data.rest.RetrofitApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }

    @Provides
    fun provideRestApi(retrofitApi: RetrofitApi): RestApi {
        return RestApi(retrofitApi)
    }

    @Provides
    fun provideRemoteDataStore(restApi: RestApi): RemoteDataStore {
        return RemoteDataStore(restApi)
    }

    @Provides
    fun provideLocalDataStore(): LocalDataStore {
        return LocalDataStore()
    }

    @Provides
    fun provideDataStoreProxy(
        localDataStore: LocalDataStore,
        remoteDataStore: RemoteDataStore
    ): DataStoreProxy {
        return DataStoreProxy(localDataStore, remoteDataStore)
    }

    @Provides
    fun provideMapperFactory() = MapperFactory()

    @Provides
    fun provideRepository(
        dataStoreProxy: DataStoreProxy,
        mapperFactory: MapperFactory
    ): Repository {
        return RepositoryImpl(dataStoreProxy, mapperFactory)
    }
}