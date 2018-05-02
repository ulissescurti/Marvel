package br.com.soulskyye.marvel.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.io.Serializable

open class Items(@Ignore @SerializedName("items") var items: ArrayList<Item>? = ArrayList(), var x: Int = 1) : RealmObject(), Serializable