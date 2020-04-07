package com.example.market_observer_android.data.repository

import android.util.Log
import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable
import okhttp3.ResponseBody

class RepositoryImpl(var dataStore: DataStoreProxy, var mapper: MapperFactory) : Repository {

    private val tag = RepositoryImpl::class.java.simpleName

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return Observable.empty()//dataStore.login(credentialsEntity)
    }

    override fun getActiveLinks(): Observable<List<ActiveLink>> {
        Log.i(tag, "getActiveLinks")
        return dataStore.getAllLinks()
    }

    override fun addLink(link: Link) {
        Log.i(tag, "addLink")
        dataStore.addLink(link)
    }

    override fun deleteLink(url: String) {
        Log.i(tag, "deleteLink")
        dataStore.deleteLink(url)
    }

    override fun addResults(url: String, results: List<LinkResult>) {
        Log.i(tag, "addResults")
        dataStore.addResults(url, results)
    }

    override fun getResults(url: String): Observable<List<LinkResult>?> {
        return dataStore.getResults(url)
    }
}