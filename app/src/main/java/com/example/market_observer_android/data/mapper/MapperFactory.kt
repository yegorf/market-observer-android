package com.example.market_observer_android.data.mapper

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.realm.RealmResults

class MapperFactory {

    fun <T> realmsToListMapper(results: RealmResults<T>): List<T> {
        val list = mutableListOf<T>()
        results.forEach {
            list.add(it)
        }
        return list
    }

//    fun realmLinkMapper(): Mapper<LinkRealm, ActiveLink> {
//        return object : Mapper<LinkRealm, ActiveLink> {
//            override fun transform(entity: LinkRealm): ActiveLink {
//                return ActiveLink(Link(entity.url, entity.name, entity.periodicity),)
//            }
//        }
//    }

    fun realmLinkResultMapper(): Mapper<LinkResultRealm, LinkResult> {
        return object : Mapper<LinkResultRealm, LinkResult> {
            override fun transform(entity: LinkResultRealm): LinkResult {
                return LinkResult(
                    entity.url,
                    entity.title,
                    entity.imageUrl,
                    entity.time,
                    entity.location
                )
            }
        }
    }

    fun realmLinkListMapper(): Mapper<List<LinkRealm>, List<ActiveLink>> {
        return object : Mapper<List<LinkRealm>, List<ActiveLink>> {
            override fun transform(entity: List<LinkRealm>): List<ActiveLink> {
                val links = mutableListOf<ActiveLink>()
                entity.forEach {
                    val results = mutableListOf<LinkResult>()
                    it.results?.forEach { res ->
                        results.add(realmLinkResultMapper().transform(res))
                    }

                    links.add(
                        ActiveLink(
                            Link(it.url, it.name, it.periodicity),
                            results
                        )
                    )
                }
                return links
            }
        }
    }
}