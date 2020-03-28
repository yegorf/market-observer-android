package com.example.market_observer_android.domain.injection

import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.domain.service.MonitoringService
import dagger.Component

@Component(modules = [DataModule::class])
interface DomainComponent {

    fun inject(monitoringService: MonitoringService)
}