package com.example.market_observer_android.data.mapper

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.data.local.realm_entity.SavedResultRealm
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.realm.RealmList
import io.realm.RealmResults
import java.util.*

class MapperFactory {

    fun <T> realmsToListMapper(results: RealmResults<T>): List<T> {
        val list = mutableListOf<T>()
        list.addAll(results)
        return list
    }

    fun <T> listToRealmListMapper(list: List<T>): RealmList<T> {
        val realm = RealmList<T>()
        realm.addAll(list)
        return realm
    }

    fun linkToRealmMapper(): Mapper<Link, LinkRealm> {
        return object : Mapper<Link, LinkRealm> {
            override fun transform(link: Link): LinkRealm {
                val realm = LinkRealm()
                realm.url = link.url
                realm.name = link.name
                realm.periodicity = link.periodicity
                realm.isActive = link.isActive

                val realms = mutableListOf<LinkResultRealm>()
                link.results.forEach {
                    realms.add(resultToRealmMapper().transform(it))
                }

                realm.results = listToRealmListMapper(realms)
                return realm
            }
        }
    }

//    fun realmLinkMapper(): Mapper<LinkRealm, ActiveLink> {
//        return object : Mapper<LinkRealm, ActiveLink> {
//            override fun transform(entity: LinkRealm): ActiveLink {
//                return ActiveLink(Link(entity.url, entity.name, entity.periodicity),)
//            }
//        }
//    }

    fun realmLinkResultListMapper(): Mapper<List<LinkResultRealm>, List<LinkResult>> {
        return object : Mapper<List<LinkResultRealm>, List<LinkResult>> {
            override fun transform(entity: List<LinkResultRealm>): List<LinkResult> {
                val list = mutableListOf<LinkResult>()
                entity.forEach {
                    list.add(realmLinkResultMapper().transform(it))
                }
                return list
            }
        }
    }

    fun realmLinkResultMapper(): Mapper<LinkResultRealm, LinkResult> {
        return object : Mapper<LinkResultRealm, LinkResult> {
            override fun transform(entity: LinkResultRealm): LinkResult {
                return LinkResult(
                    entity.url,
                    entity.title,
                    entity.imageUrl,
                    entity.time,
                    entity.location,
                    entity.price
                )
            }
        }
    }

    fun realmLinkListMapper(): Mapper<List<LinkRealm>, List<Link>> {
        return object : Mapper<List<LinkRealm>, List<Link>> {
            override fun transform(entity: List<LinkRealm>): List<Link> {
                val links = mutableListOf<Link>()
                entity.forEach {
                    val results = mutableListOf<LinkResult>()
                    it.results?.forEach { res ->
                        results.add(realmLinkResultMapper().transform(res))
                    }

                    links.add(
                        Link(it.url, it.name, it.periodicity, it.isActive, results)
                    )
                }
                return links
            }
        }
    }

    fun resultToRealmMapper(): Mapper<LinkResult, LinkResultRealm> {
        return object : Mapper<LinkResult, LinkResultRealm> {
            override fun transform(entity: LinkResult): LinkResultRealm {
                val realm = LinkResultRealm()
                realm.id = UUID.randomUUID().toString()
                realm.title = entity.title
                realm.url = entity.url
                realm.imageUrl = entity.imageUrl
                realm.location = entity.location
                realm.time = entity.time
                realm.price = entity.price
                return realm
            }
        }
    }
}