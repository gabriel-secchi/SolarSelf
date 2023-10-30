package com.gma.data_local.implementationDao

import io.realm.kotlin.Realm
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.TRUE_PREDICATE
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

abstract class CustomDaoImpl<T : RealmObject>(
    private val database: Realm,
    private val kclass: KClass<T>,
) {

    protected fun add(_object: T) {
        database.writeBlocking {
            copyToRealm(_object)
        }
    }

    protected fun update(_object: T, writeUpdateObject: (T) -> Unit) {
        database.writeBlocking {
            writeUpdateObject(_object)
        }
    }

    private fun customFind(
        key: String? = null,
        comparation: String? = null,
        value: String? = null
    ): RealmResults<T> {
        var filter = TRUE_PREDICATE
        if (key != null && comparation != null && value != null)
            filter = "$key $comparation $value"

        return database.query(
            clazz = kclass,
            query = filter
        ).find()
    }

    protected fun find(key: String, value: String): List<T>? {
        return try {
            val objectToEdit: RealmResults<T> = customFind(key, EQUALS, value)
            objectToEdit.map {
                it
            }
        } catch (ex: Exception) {
            null
        }
    }

    protected fun findFirst(): T? {
        return try {
            val objectToEdit: RealmResults<T> = customFind()
            objectToEdit.first()
        } catch (ex: Exception) {
            null
        }
    }

    protected fun delete(_object: T) {
        database.writeBlocking {
            findLatest(_object)?.also {
                delete(it)
            }
        }
    }

    companion object {
        private const val EQUALS = "=="
    }
}