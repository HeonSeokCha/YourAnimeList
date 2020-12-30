package com.chs.youranimelist

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

object Binding {
    @BindingAdapter("imageSrc")
    @JvmStatic
    fun loadImg(imageView: ImageView,path: String) {
        if(path != null) {
            Glide.with(imageView.context).load(path)
                .into(imageView)
        }
    }

    @BindingAdapter("trailerBtn")
    @JvmStatic
    fun trailerChk(floatingActionButton: FloatingActionButton,path:String) {
        if(path == null) {
            floatingActionButton.visibility = View.GONE
        }
    }
}