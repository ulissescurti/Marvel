package br.com.soulskyye.marvel.data.network

import br.com.soulskyye.marvel.data.network.model.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int,
                      @Query("offset") offset: Int): Single<CharactersResponse>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId")characterId: String): Single<CharactersResponse>

}