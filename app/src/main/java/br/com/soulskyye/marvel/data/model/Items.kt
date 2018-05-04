package br.com.soulskyye.marvel.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class Items(@SerializedName("items") var items: RealmList<Item> = RealmList()) : RealmObject()