package br.com.soulskyye.marvel.data

import br.com.soulskyye.marvel.data.db.DbHelper
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.data.network.ApiService
import br.com.soulskyye.marvel.data.network.model.CharactersResponse
import io.reactivex.Observable
import io.reactivex.Single

class DataManager (private val apiManager: ApiManager): ApiService, DbHelper {

    override fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse> {
        return apiManager.getCharacters(limit, offset)
    }

    override fun getFavorites(): Observable<List<Character>> {
        return Observable.just(mutableListOf())
    }

}