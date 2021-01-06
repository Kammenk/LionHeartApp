package com.example.lionheartapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

object ImageSetter {
    fun setImage(imageURL: String): Bitmap {
        val inputStream = URL(imageURL).openStream()
        return BitmapFactory.decodeStream(inputStream)
    }
}