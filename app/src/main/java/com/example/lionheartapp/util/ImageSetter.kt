package com.example.lionheartapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

object ImageSetter {
    //Takes image from url and converts it to a bitmap so it can be attached to imageview
    fun setImage(imageURL: String): Bitmap {
        val inputStream = URL(imageURL).openStream()
        return BitmapFactory.decodeStream(inputStream)
    }
}