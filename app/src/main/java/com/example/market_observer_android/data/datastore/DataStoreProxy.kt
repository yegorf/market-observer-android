package com.example.market_observer_android.data.datastore

class DataStoreProxy(var localDataStore: LocalDataStore, var remoteDataStore: RemoteDataStore) :
    DataStore {
}