package com.dteti.animapp.common

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dteti.animapp.dto.UriType

class BindingAdapters {
    companion object {
        @BindingAdapter("imageUri", "imageUriType", requireAll = true)
        @JvmStatic
        fun loadImage(view: ImageView, uri: String?, type: UriType?) {
            Glide.with(view.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(view)
        }

        @BindingAdapter("activeTint")
        @JvmStatic
        fun tintIfTrue(view: ImageView, isActive: Boolean?) {
            if (isActive != null && isActive) {
                view.setColorFilter(Color.RED)
            } else {
                view.setColorFilter(Color.GRAY)
            }
        }
    }
}