package br.com.soulskyye.marvel.data.network.model

import br.com.soulskyye.marvel.data.model.Character
import com.google.gson.annotations.SerializedName

class CharactersResponse {

    @SerializedName("code")
    var code: Int = 0

    @SerializedName("status")
    var status: String = ""

    @SerializedName("data")
    var data: Data? = null

    inner class Data(
            @SerializedName("offset")
            var offset: Int = 0,

            @SerializedName("limit")
            var limit: Int = 0,

            @SerializedName("total")
            var total: Int = 0,

            @SerializedName("count")
            var count: Int = 0,

            @SerializedName("results")
            var results: ArrayList<Character>? = null
    )

}