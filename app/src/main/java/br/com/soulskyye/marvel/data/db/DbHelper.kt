package br.com.soulskyye.marvel.data.db

import br.com.soulskyye.marvel.data.model.Character
import io.reactivex.Observable

interface DbHelper {

    fun getFavorites(): Observable<List<Character>>

}