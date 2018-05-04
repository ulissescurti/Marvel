package br.com.soulskyye.marvel.data.db

import br.com.soulskyye.marvel.data.model.Character
import io.reactivex.Single

/**
 * Defines the Database methods
 */
interface DaoAccess {

    fun getFavorite(character: Character): Single<Character>

    fun getFavorites(): Single<List<Character>>

    fun insertFavorite(character: br.com.soulskyye.marvel.data.model.Character)

    fun deleteFavorite (character: br.com.soulskyye.marvel.data.model.Character)

}