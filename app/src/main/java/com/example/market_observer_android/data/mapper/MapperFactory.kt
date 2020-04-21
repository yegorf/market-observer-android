package com.example.market_observer_android.data.mapper

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import com.google.firebase.auth.FirebaseAuth
import io.realm.RealmList
import io.realm.RealmResults
import java.util.*
import kotlin.collections.HashMap

class MapperFactory {

    fun <T> mapRealmListToList(results: RealmResults<T>): List<T> {
        val list = mutableListOf<T>()
        list.addAll(results)
        return list
    }

    fun <T> mapListToRealmList(list: List<T>): RealmList<T> {
        val realm = RealmList<T>()
        realm.addAll(list)
        return realm
    }

    fun linkToRealmMapper(): Mapper<Link, LinkRealm> {
        return object : Mapper<Link, LinkRealm> {
            override fun transform(entity: Link): LinkRealm {
                val realm = LinkRealm()
                realm.url = entity.url
                realm.name = entity.name
                realm.periodicity = entity.periodicity
                realm.isActive = entity.isActive

                val realms = mutableListOf<LinkResultRealm>()
                entity.results.forEach {
                    realms.add(resultToRealmMapper().transform(it))
                }

                realm.results = mapListToRealmList(
                    entity.results.map {
                        resultToRealmMapper().transform(it)
                    }
                )

                return realm
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

    fun realmLinkToLinkMapper(): Mapper<LinkRealm, Link> {
        return object : Mapper<LinkRealm, Link> {
            override fun transform(entity: LinkRealm): Link {
                val link = Link()
                link.url = entity.url
                link.name = entity.name
                link.periodicity = entity.periodicity
                link.isActive = entity.isActive
                if (entity.results != null) {
                    link.results = entity.results!!.map {
                        realmLinkResultMapper().transform(it)
                    }
                }
                return link
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

    fun resultToMapMapper(): Mapper<LinkResult, HashMap<String, String?>> {
        return object : Mapper<LinkResult, HashMap<String, String?>> {
            override fun transform(entity: LinkResult): HashMap<String, String?> {
                return hashMapOf(
                    "url" to entity.url,
                    "title" to entity.title,
                    "imageUrl" to entity.imageUrl,
                    "price" to entity.price,
                    "location" to entity.location,
                    "time" to entity.time
                )
            }
        }
    }
}