package br.com.soulskyye.marvel.data

import br.com.soulskyye.marvel.data.db.DaoAccess
import br.com.soulskyye.marvel.data.db.DatabaseManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.data.network.ApiService
import br.com.soulskyye.marvel.data.network.RetrofitException
import br.com.soulskyye.marvel.data.network.model.CharactersResponse
import io.reactivex.Flowable
import io.reactivex.Single

class DataManager(private val apiManager: ApiManager,
                  private val databaseManager: DatabaseManager) : ApiService, DaoAccess {

    override fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse> {
        return apiManager.getCharacters(limit, offset)
    }

    override fun getFavorite(character: Character): Single<Character> {
        return databaseManager.getFavorite(character)
    }

    override fun getFavorites(): Single<List<Character>> {
        return databaseManager.getFavorites()
    }

    override fun insertFavorite(character: Character) {
        databaseManager.insertFavorite(character)
    }

    override fun deleteFavorite(character: Character) {
        databaseManager.deleteFavorite(character)
    }

    fun asRetrofitException(throwable: Throwable): RetrofitException {
        return apiManager.asRetrofitException(throwable)
    }

}