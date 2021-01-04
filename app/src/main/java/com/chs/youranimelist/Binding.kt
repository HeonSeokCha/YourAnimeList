package com.chs.youranimelist

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chs.youranimelist.network.dto.Trailer
import com.google.android.material.floatingactionbutton.FloatingActionButton

object Binding {
    @BindingAdapter("imageSrc")
    @JvmStatic
    fun loadImg(imageView: ImageView,path: String?) {
        Glide.with(imageView.context).load(path)
            .into(imageView)
    }

    @BindingAdapter("trailerBtn")
    @JvmStatic
    fun trailerChk(floatingActionButton: FloatingActionButton, trailer: Trailer?) {
        Log.d("trailer","$trailer")
        if(trailer?.id?.isNotEmpty() == true) floatingActionButton.visibility = View.GONE
    }

    @BindingAdapter("htmlTags")
    @JvmStatic
    fun setHtmlTags(textView: TextView,string: String?) {
        if(string?.isNotEmpty() == true)
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}