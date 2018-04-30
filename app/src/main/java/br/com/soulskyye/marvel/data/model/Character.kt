package br.com.soulskyye.marvel.data.model

import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Character(@PrimaryKey @NonNull @SerializedName("id") var id: String = "",
                     @SerializedName("name") var name: String = "",
                     @SerializedName("description") var description: String = "",
                     @SerializedName("thumbnail") var thumbnail: Thumbnail? = null,
                     @Ignore var isFavorite: Boolean = false) : RealmObject(), Serializable