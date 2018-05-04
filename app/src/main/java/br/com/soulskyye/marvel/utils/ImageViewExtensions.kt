package br.com.soulskyye.marvel.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.AppCompatImageView
import br.com.soulskyye.marvel.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

/**
 * Extension method to load image into ImageView using picasso
 */
fun AppCompatImageView.loadImage(url: String?, placeholder: Int, callback: (Bitmap) -> Unit) {
    if (url != null && url.isNotBlank()) {
        Picasso.get()
                .load(url)
                .placeholder(placeholder)
                .error(placeholder)
                .into(this, object : Callback {
                    override fun onSuccess() {
                        callback((drawable as BitmapDrawable).bitmap)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
    } else {
        Picasso.get().load(placeholder)
    }
}