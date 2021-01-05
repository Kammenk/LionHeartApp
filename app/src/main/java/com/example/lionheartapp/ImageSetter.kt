package com.example.lionheartapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream
import java.net.URL

object ImageSetter {
    fun setImage(imageURL: String): Bitmap {
        var inputStream = URL(imageURL).openStream()
        var bitmap = BitmapFactory.decodeStream(inputStream)
        return bitmap
    }
}