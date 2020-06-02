package com.example.market_observer_android.data.mapper

import com.example.market_observer_android.data.entity.LinkEntity
import com.example.market_observer_android.data.entity.ResultEntity
import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import java.util.*

class MapperFactory {

    fun linkToRealmMapper(): Mapper<Link, LinkRealm> {
        return object : Mapper<Link, LinkRealm> {
            override fun transform(source: Link): LinkRealm {
                val realm = LinkRealm()
                realm.url = source.url
                realm.name = source.name
                realm.periodicity = source.periodicity
                realm.isActive = source.isActive

                val realms = mutableListOf<LinkResultRealm>()
                source.results.forEach {
                    realms.add(resultToRealmMapper().transform(it))
                }

                realm.results = RealmListMapper.mapListToRealmList(
                    source.results.map {
                        resultToRealmMapper().transform(it)
                    }
                )

                return realm
            }
        }
    }

    fun realmLinkResultMapper(): Mapper<LinkResultRealm, LinkResult> {
        return object : Mapper<LinkResultRealm, LinkResult> {
            override fun transform(source: LinkResultRealm): LinkResult {
                return LinkResult(
                    source.url,
                    source.title,
                    source.imageUrl,
                    source.time,
                    source.location,
                    source.price,
                    source.isSaved
                )
            }
        }
    }

    fun realmLinkToLinkMapper(): Mapper<LinkRealm, Link> {
        return object : Mapper<LinkRealm, Link> {
            override fun transform(source: LinkRealm): Link {
                val link = Link()
                link.url = source.url
                link.name = source.name
                link.periodicity = source.periodicity
                link.isActive = source.isActive
                if (source.results != null) {
                    link.results = source.results!!.map {
                        realmLinkResultMapper().transform(it)
                    }
                }
                return link
            }
        }
    }

    fun resultToRealmMapper(): Mapper<LinkResult, LinkResultRealm> {
        return object : Mapper<LinkResult, LinkResultRealm> {
            override fun transform(source: LinkResult): LinkResultRealm {
                val realm = LinkResultRealm()
                realm.id = UUID.randomUUID().toString()
                realm.title = source.title
                realm.url = source.url
                realm.imageUrl = source.imageUrl
                realm.location = source.location
                realm.time = source.time
                realm.price = source.price
                realm.isSaved = source.isSaved
                return realm
            }
        }
    }

    fun resultToSavedEntityMapper(): Mapper<LinkResult, ResultEntity> {
        return object : Mapper<LinkResult, ResultEntity> {
            override fun transform(source: LinkResult): ResultEntity {
                val saved = ResultEntity()
                saved.url = source.url
                saved.title = source.title
                saved.imageUrl = source.imageUrl
                saved.location = source.location
                saved.price = source.price
                saved.time = source.time
                return saved
            }
        }
    }

    fun linkToEntityMapper(): Mapper<Link, LinkEntity> {
        return object : Mapper<Link, LinkEntity> {
            override fun transform(source: Link): LinkEntity {
                val entity = LinkEntity()
                entity.url = source.url
                entity.name = source.name
                entity.periodicity = source.periodicity
                entity.isActive = source.isActive
                entity.results = source.results
                return entity
            }
        }
    }

    fun entityToLinkMapper(): Mapper<LinkEntity, Link> {
        return object : Mapper<LinkEntity, Link> {
            override fun transform(source: LinkEntity): Link {
                val model = Link()
                model.url = source.url
                model.name = source.name
                model.periodicity = source.periodicity
                model.isActive = source.isActive
                model.results = source.results
                return model
            }
        }
    }
}