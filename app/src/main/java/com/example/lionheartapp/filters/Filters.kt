package com.example.lionheartapp.filters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.zomato.photofilters.SampleFilters
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter

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

    fun adjustBrightness(imageView: ImageView, brightness: Int, contrast: Float,saturation: Float, imageToImplement: Bitmap){
        //Adding the relevant filters and applying them to the main image view
        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(myFilter.processFilter(imageToImplement.copy(Bitmap.Config.ARGB_8888, true)))
    }

    fun adjustContrast(imageView: ImageView, contrast: Float,brightness: Int,saturation: Float, imageToImplement: Bitmap){
        //Adding the relevant filters and applying them to the main image view
        val myFilter = Filter()
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(myFilter.processFilter(imageToImplement.copy(Bitmap.Config.ARGB_8888, true)))
    }

    fun adjustSaturation(imageView: ImageView, saturation: Float, contrast: Float,brightness: Int, imageToImplement: Bitmap){
        //Adding the relevant filters and applying them to the main image view
        val myFilter = Filter()
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        imageView.setImageBitmap(myFilter.processFilter(imageToImplement.copy(Bitmap.Config.ARGB_8888, true)))
    }

    private fun convertAndBindImage(imageView: ImageView,filter: Filter){
        //Converts an image view to bitmap, applies filter and binds it to the main image view
        val bitmapDrawable: BitmapDrawable = imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val copyImage: Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true)
        val outputImage: Bitmap = filter.processFilter(copyImage)

        imageView.setImageBitmap(outputImage)
    }

}