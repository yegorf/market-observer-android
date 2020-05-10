package com.example.market_observer_android.data.injection

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.datastore.LocalDataStore
import com.example.market_observer_android.data.datastore.RemoteDataStore
import com.example.market_observer_android.data.firebase.FirebaseService
import com.example.market_observer_android.data.local.RealmService
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.data.repository.RepositoryImpl
import com.example.market_observer_android.data.util.RemoteDownloadManager
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class DataModule {

    @Provides
    fun provideRemoteDataStore(
        firebaseService: FirebaseService,
        mapperFactory: MapperFactory
    ): RemoteDataStore {
        return RemoteDataStore(firebaseService, mapperFactory)
    }

    @Provides
    fun provideLocalDataStore(mapper: MapperFactory, realm: RealmService): LocalDataStore {
        return LocalDataStore(realm, mapper)
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
    fun provideRealmService(): RealmService {
        return RealmService(Realm.getDefaultInstance())
    }

    @Provides
    fun provideFirebaseService(): FirebaseService {
        return FirebaseService()
    }

    @Provides
    fun provideRepository(
        dataStoreProxy: DataStoreProxy,
        mapperFactory: MapperFactory
    ): Repository {
        return RepositoryImpl(dataStoreProxy, mapperFactory)
    }

    @Provides
    fun provideRemoteDownloadManager(
        localDataStore: LocalDataStore,
        remoteDataStore: RemoteDataStore
    ) = RemoteDownloadManager(localDataStore, remoteDataStore)
}