package com.example.lionheartapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.view.*
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.ImageSetter
import com.example.lionheartapp.R
import com.example.lionheartapp.filters.Filters
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditImageFragment : Fragment(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private val args by navArgs<EditImageFragmentArgs>()
    private lateinit var currentImage: ImageView

    //Filters
    private lateinit var noFilter: ImageView
    private lateinit var starLitFilter: ImageView
    private lateinit var blueMessFilter: ImageView
    private lateinit var aweStruckVibeFilter: ImageView
    private lateinit var limeStutterFilter: ImageView
    private lateinit var nightWhisperFilter: ImageView

    //Seekbars
    private lateinit var brightnessSeekBar: SeekBar
    private lateinit var contrastSeekBar: SeekBar
    private lateinit var saturationSeekBar: SeekBar

    //Seekbar value variables
    private var brightnessProgress: Int = 0
    private var contrastProgress: Float = 1.0f
    private var saturationProgress: Float = 1.0f

    //Images
    private lateinit var originalImage: Bitmap
    // to backup image with filter applied
    private lateinit var filteredImage: Bitmap
    // the final image after applying brightness, saturation, contrast
    private lateinit var finalImage: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_image, container, false)

        //Used when sending images
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        //Hiding bottom navigation
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Enabling top menu
        setHasOptionsMenu(true)

        //Variable initialization
        currentImage = view.findViewById(R.id.currentPhoto)

        noFilter = view.findViewById(R.id.noFilter)
        starLitFilter = view.findViewById(R.id.starLitFilter)
        blueMessFilter = view.findViewById(R.id.blueMessFilter)
        aweStruckVibeFilter = view.findViewById(R.id.aweStruckVibeFilter)
        limeStutterFilter = view.findViewById(R.id.limeStutterFilter)
        nightWhisperFilter = view.findViewById(R.id.nightWhisperFilter)

        brightnessSeekBar = view.findViewById(R.id.brightnessSeekbar)
        contrastSeekBar = view.findViewById(R.id.contrastSeekbar)
        saturationSeekBar = view.findViewById(R.id.saturationSeekbar)

        bindImages()
        filterSetup()
        seekBarSetup()

        return view
    }

    private fun seekBarSetup() {
        //Brightness setup and values(in integer)
        brightnessSeekBar.max = 200
        brightnessSeekBar.progress = 100

        //Contrast setup and values(in floats)
        contrastSeekBar.max = 20
        contrastSeekBar.progress = 10

        //Saturation setup and values(in floats)
        saturationSeekBar.max = 30
        saturationSeekBar.progress = 15

        brightnessSeekBar.setOnSeekBarChangeListener(this)
        contrastSeekBar.setOnSeekBarChangeListener(this)
        saturationSeekBar.setOnSeekBarChangeListener(this)
    }

    private fun bindImages() {
        //Binding images to the respective views
        currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))

        val bitmapDrawable: BitmapDrawable = currentImage.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true)
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true)

        noFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlSmall))
        starLitFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlSmall))
        blueMessFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlSmall))
        aweStruckVibeFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlSmall))
        limeStutterFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlSmall))
        nightWhisperFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlSmall))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareEditedImage -> {
                shareImage(requireContext(), currentImage)
            }
            R.id.downloadEditedImage -> {
                saveImageOnDevice(currentImage)
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private fun saveImageOnDevice(imageView: ImageView) {

    }

    //Used to share an image after its been filtered
    private fun shareImage(context: Context, imageView: ImageView) {
        //Converting image to bitmap first
        val myDrawable = imageView.drawable
        val bitmap = (myDrawable as BitmapDrawable).bitmap
        val intent = Intent()
        intent.apply {
            this.action = Intent.ACTION_SEND
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap,context))
        }
        context.startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun getLocalBitmapUri(bmp: Bitmap, context: Context): Uri? {
        var bmpUri: Uri? = null
        try {
            //Saving the file temporarily
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val fileOutputStream = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
            fileOutputStream.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.noFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
            }
            R.id.starLitFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyStarLitFilter(currentImage)
            }
            R.id.blueMessFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyBlueMessFilter(currentImage)
            }
            R.id.aweStruckVibeFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyAweStruckVibeFilter(currentImage)
            }
            R.id.limeStutterFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyLimeStutterFilter(currentImage)
            }
            R.id.nightWhisperFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyNightWhisperFilter(currentImage)
            }
        }
    }

    private fun filterSetup() {
        //Applying filter to the relevant image views
        Filters.applyStarLitFilter(starLitFilter)
        Filters.applyBlueMessFilter(blueMessFilter)
        Filters.applyAweStruckVibeFilter(aweStruckVibeFilter)
        Filters.applyLimeStutterFilter(limeStutterFilter)
        Filters.applyNightWhisperFilter(nightWhisperFilter)

        noFilter.setOnClickListener(this)
        starLitFilter.setOnClickListener(this)
        blueMessFilter.setOnClickListener(this)
        aweStruckVibeFilter.setOnClickListener(this)
        limeStutterFilter.setOnClickListener(this)
        nightWhisperFilter.setOnClickListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        if (seekBar != null) {
            if (seekBar.id == R.id.brightnessSeekbar) {
                // brightness values are b/w -100 to +100
                    brightnessProgress = progress - 100
                Filters.adjustBrightness(currentImage,brightnessProgress,contrastProgress,saturationProgress,finalImage)
            }

            if (seekBar.id == R.id.contrastSeekbar) {
                //Converting value to float because of the filter function
                val progressInFloat: Float = .10f * (progress + 10)
                contrastProgress = progressInFloat
                Filters.adjustContrast(currentImage,contrastProgress,brightnessProgress,contrastProgress,finalImage)
            }

            if (seekBar.id == R.id.saturationSeekbar) {
                //Converting value to float because of the filter function
                val progressInFloat: Float = .10f * progress
                saturationProgress = progressInFloat
                Filters.adjustSaturation(currentImage,saturationProgress,contrastProgress,brightnessProgress,finalImage)
            }
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        return
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        return
    }
}