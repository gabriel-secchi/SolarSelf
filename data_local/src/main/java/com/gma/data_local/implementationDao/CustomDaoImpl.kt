package com.gma.data_local.implementationDao

import io.realm.kotlin.Realm
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.TRUE_PREDICATE
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
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

    private fun _find(
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

    protected fun findById(id: ObjectId): T? {
        return try {
            val objectToEdit: RealmResults<T> = _find(ID, EQUALS, id.toString())
            objectToEdit.first()
        } catch (ex: Exception) {
            null
        }
    }

    protected fun find(key: String, value: String): List<T>? {
        return try {
            val objectToEdit: RealmResults<T> = _find(key, EQUALS, value)
            objectToEdit.map {
                it
            }
        } catch (ex: Exception) {
            null
        }
    }

    protected fun findFirst(): T? {
        return try {
            val objectToEdit: RealmResults<T> = _find()
            objectToEdit.first()
        } catch (ex: Exception) {
            null
        }
    }

    private companion object {
        const val EQUALS = "=="
        const val ID = "_id"
    }
}