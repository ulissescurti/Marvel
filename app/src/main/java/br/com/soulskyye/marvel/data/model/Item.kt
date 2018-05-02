package br.com.soulskyye.marvel.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.io.Serializable

open class Item(@SerializedName("name") var name: String = "",
                @SerializedName("resourceURI") var image: String = "") : RealmObject(), Serializable