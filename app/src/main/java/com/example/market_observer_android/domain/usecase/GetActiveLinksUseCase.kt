package com.example.market_observer_android.domain.usecase

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.ActiveLink
import io.reactivex.Observable
import javax.inject.Inject

class GetActiveLinksUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<List<ActiveLink>>() {

    override fun getObservable(): Observable<List<ActiveLink>> {
        return repository.getActiveLinks()
    }
}