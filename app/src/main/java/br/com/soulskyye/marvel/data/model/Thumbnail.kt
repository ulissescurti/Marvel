package br.com.soulskyye.marvel.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Thumbnail(@SerializedName("path") var path: String? = "",
                     @SerializedName("extension") var extension: String? = "") : RealmObject()