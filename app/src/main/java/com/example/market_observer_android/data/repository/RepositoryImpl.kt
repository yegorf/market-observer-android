package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.mapper.MapperFactory

class RepositoryImpl(var dataStore: DataStoreProxy, var mapper: MapperFactory) : Repository {

}