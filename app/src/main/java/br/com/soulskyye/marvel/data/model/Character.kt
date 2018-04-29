package br.com.soulskyye.marvel.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Character(@SerializedName("id") var id: Long,
                     @SerializedName("name") var name: String,
                     @SerializedName("description") var description: String,
                     @SerializedName("thumbnail") var thumbnail: Thumbnail?): Serializable {

    inner class Thumbnail(@SerializedName("path") var path: String?,
                          @SerializedName("extension") var extension: String?): Serializable
}