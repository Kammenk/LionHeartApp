package com.example.lionheartapp.filters

import android.graphics.Bitmap
import android.widget.ImageView
import com.zomato.photofilters.SampleFilters
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter

object Filters {

    fun applyStarLitFilter(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Applies specific filter with the relevant custom attribute if they were changes(i.e brightness was scaled up)
        val filter = SampleFilters.getStarLitFilter()
        filter.addSubFilter(BrightnessSubFilter(brightness))
        filter.addSubFilter(ContrastSubFilter(contrast))
        filter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            filter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun applyBlueMessFilter(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Applies specific filter with the relevant custom attribute if they were changes(i.e brightness was scaled up)
        val filter = SampleFilters.getBlueMessFilter()
        filter.addSubFilter(BrightnessSubFilter(brightness))
        filter.addSubFilter(ContrastSubFilter(contrast))
        filter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            filter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun applyAweStruckVibeFilter(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Applies specific filter with the relevant custom attribute if they were changes(i.e brightness was scaled up)
        val filter = SampleFilters.getAweStruckVibeFilter()
        filter.addSubFilter(BrightnessSubFilter(brightness))
        filter.addSubFilter(ContrastSubFilter(contrast))
        filter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            filter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun applyLimeStutterFilter(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Applies specific filter with the relevant custom attribute if they were changes(i.e brightness was scaled up)
        val filter = SampleFilters.getLimeStutterFilter()
        filter.addSubFilter(BrightnessSubFilter(brightness))
        filter.addSubFilter(ContrastSubFilter(contrast))
        filter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            filter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun applyNightWhisperFilter(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Applies specific filter with the relevant custom attribute if they were changes(i.e brightness was scaled up)
        val filter = SampleFilters.getNightWhisperFilter()
        filter.addSubFilter(BrightnessSubFilter(brightness))
        filter.addSubFilter(ContrastSubFilter(contrast))
        filter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            filter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun adjustBrightness(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Adding the relevant filters and applying them to the main image view
        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            myFilter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun adjustContrast(
        imageView: ImageView,
        contrast: Float,
        brightness: Int,
        saturation: Float,
        imageToImplement: Bitmap
    ) {
        //Adding the relevant filters and applying them to the main image view
        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            myFilter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    fun adjustSaturation(
        imageView: ImageView,
        saturation: Float,
        contrast: Float,
        brightness: Int,
        imageToImplement: Bitmap
    ) {
        //Adding the relevant filters and applying them to the main image view
        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            myFilter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

    private fun convertAndBindImage(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap,
        filter: Filter
    ) {
        //Applies filter and binds it to the main image view
//        val bitmapDrawable: BitmapDrawable = imageView.drawable as BitmapDrawable
//        val bitmap = bitmapDrawable.bitmap
//        val copyImage: Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true)
//        val outputImage: Bitmap = filter.processFilter(copyImage)
//
//        imageView.setImageBitmap(outputImage)
        filter.addSubFilter(BrightnessSubFilter(brightness))
        filter.addSubFilter(ContrastSubFilter(contrast))
        filter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            filter.processFilter(
                imageToImplement.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
        )
    }

}