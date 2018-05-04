package br.com.soulskyye.marvel.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Item(@SerializedName("name") var name: String = "",
                @SerializedName("resourceURI") var resource: String = "") : RealmObject()