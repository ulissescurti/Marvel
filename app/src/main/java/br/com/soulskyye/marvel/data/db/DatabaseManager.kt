package br.com.soulskyye.marvel.data.db

import br.com.soulskyye.marvel.data.model.Character
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class DatabaseManager : DaoAccess {

    override fun getFavorite(character: Character): Single<Character> {
        return Single.create<Character> { emitter ->
            Realm.getDefaultInstance().use { realm ->
                val results = realm.where<Character>()
                        .equalTo("id", character.id)
                        .findFirst()
                try {
                    val char = realm.copyFromRealm(results)

                    if(char == null) {
                        emitter.onSuccess(character)
                    } else {
                        character.isFavorite = true
                        emitter.onSuccess(character)
                    }
                } catch (exception: IllegalArgumentException){

                    emitter.onSuccess(character)
                }
            }
        }
    }

    override fun getFavorites(): Single<List<Character>> {
        return Single.create<List<Character>> { emitter ->
            Realm.getDefaultInstance().use { realm ->
                val results = realm.where<Character>()
                        .findAll()
                try {
                    val list = realm.copyFromRealm(results)

                    if(list == null) {
                        emitter.onSuccess(mutableListOf())
                    } else {
                        emitter.onSuccess(list)
                    }

                } catch (exception: IllegalArgumentException){
                    emitter.onSuccess(mutableListOf())
                }
            }
        }
    }

    override fun insertFavorite(character: Character) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            try {
                realm.insert(character)
            } catch (ex: Exception){

            }
        }
    }

    override fun deleteFavorite(character: Character) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            try {
                val rows = realm.where(Character::class.java).equalTo("id", character.id).findAll()
                rows.deleteAllFromRealm()
            } catch (ex: Exception){

            }
        }
    }
}