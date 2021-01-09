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

    fun applyCustomFilters(
        imageView: ImageView,
        brightness: Int,
        contrast: Float,
        saturation: Float,
        imageToImplement: Bitmap,
        filterToApply: Int
    ) {
        //Adding the relevant filters and applying them to the main image view
        var myFilter = Filter()
        when (filterToApply) {
            0 -> {
                myFilter = Filter()
            }
            1 -> {
                myFilter = SampleFilters.getStarLitFilter()
            }
            2 -> {
                myFilter = SampleFilters.getBlueMessFilter()
            }
            3 -> {
                myFilter = SampleFilters.getAweStruckVibeFilter()
            }
            4 -> {
                myFilter = SampleFilters.getLimeStutterFilter()
            }
            5 -> {
                myFilter = SampleFilters.getNightWhisperFilter()
            }
        }
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        myFilter.addSubFilter(SaturationSubFilter(saturation))
        imageView.setImageBitmap(
            myFilter.processFilter(
                imageToImplement.copy(Bitmap.Config.ARGB_8888, true)
            )
        )
    }
}