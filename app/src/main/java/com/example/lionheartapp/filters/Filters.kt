package com.example.lionheartapp.filters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.zomato.photofilters.SampleFilters
import com.zomato.photofilters.imageprocessors.Filter

object Filters {

    fun applyStarLitFilter(imageView: ImageView) {
        val filter = SampleFilters.getStarLitFilter()
        convertAndBindImage(imageView,filter)
    }

    fun applyBlueMessFilter(imageView: ImageView) {
        val filter = SampleFilters.getBlueMessFilter()
        convertAndBindImage(imageView,filter)
    }

    fun applyAweStruckVibeFilter(imageView: ImageView) {
        val filter = SampleFilters.getAweStruckVibeFilter()
        convertAndBindImage(imageView,filter)
    }

    fun applyLimeStutterFilter(imageView: ImageView) {
        val filter = SampleFilters.getLimeStutterFilter()
        convertAndBindImage(imageView,filter)
    }

    fun applyNightWhisperFilter(imageView: ImageView) {
        val filter = SampleFilters.getNightWhisperFilter()
        convertAndBindImage(imageView,filter)
    }

    private fun convertAndBindImage(imageView: ImageView,filter: Filter){
        val bitmapDrawable: BitmapDrawable = imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val copyImage: Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true)
        val outputImage: Bitmap = filter.processFilter(copyImage)

        imageView.setImageBitmap(outputImage)
    }

}