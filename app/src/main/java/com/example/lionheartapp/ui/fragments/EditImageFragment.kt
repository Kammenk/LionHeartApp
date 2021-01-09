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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.util.ImageSetter
import com.example.lionheartapp.R
import com.example.lionheartapp.filters.Filters
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
    private var filterSelected: Int = 0

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

    //Used for reset
    private lateinit var resetBtn: TextView

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
        resetBtn = view.findViewById(R.id.resetBtn)

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
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
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
            this.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context))
        }
        //Opening a new activity with share options
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
        //Applying different filters to our image
        when (p0!!.id) {
            R.id.noFilter -> {
                filterSelected = 0
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
            }
            R.id.starLitFilter -> {
                filterSelected = 1
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyStarLitFilter(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    finalImage
                )
            }
            R.id.blueMessFilter -> {
                filterSelected = 2
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyBlueMessFilter(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    finalImage
                )
            }
            R.id.aweStruckVibeFilter -> {
                filterSelected = 3
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyAweStruckVibeFilter(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    finalImage
                )
            }
            R.id.limeStutterFilter -> {
                filterSelected = 4
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyLimeStutterFilter(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    finalImage
                )
            }
            R.id.nightWhisperFilter -> {
                filterSelected = 5
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
                Filters.applyNightWhisperFilter(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    finalImage
                )
            }
            R.id.resetBtn -> {
                filterSelected = 0
                brightnessSeekBar.progress = 100
                contrastSeekBar.progress = 10
                saturationSeekBar.progress = 15
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
            }
        }
    }

    private fun filterSetup() {
        //Applying filter to the relevant image views
        Filters.applyStarLitFilter(
            starLitFilter,
            brightnessProgress,
            contrastProgress,
            saturationProgress,
            finalImage
        )
        Filters.applyBlueMessFilter(
            blueMessFilter,
            brightnessProgress,
            contrastProgress,
            saturationProgress,
            finalImage
        )
        Filters.applyAweStruckVibeFilter(
            aweStruckVibeFilter,
            brightnessProgress,
            contrastProgress,
            saturationProgress,
            finalImage
        )
        Filters.applyLimeStutterFilter(
            limeStutterFilter,
            brightnessProgress,
            contrastProgress,
            saturationProgress,
            finalImage
        )
        Filters.applyNightWhisperFilter(
            nightWhisperFilter,
            brightnessProgress,
            contrastProgress,
            saturationProgress,
            finalImage
        )

        noFilter.setOnClickListener(this)
        starLitFilter.setOnClickListener(this)
        blueMessFilter.setOnClickListener(this)
        aweStruckVibeFilter.setOnClickListener(this)
        limeStutterFilter.setOnClickListener(this)
        nightWhisperFilter.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        //Changing the image's brightness, contrast and saturation using the seekbars
        if (seekBar != null) {
            if (seekBar.id == R.id.brightnessSeekbar) {
                // brightness values are b/w -100 to +100
                brightnessProgress = progress - 100
                Filters.applyCustomFilters(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    originalImage,
                    filterSelected
                )
            }

            if (seekBar.id == R.id.contrastSeekbar) {
                //Converting value to float because of the filter function
                val progressInFloat: Float = .10f * progress
                contrastProgress = progressInFloat
                Filters.applyCustomFilters(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    originalImage,
                    filterSelected
                )
            }

            if (seekBar.id == R.id.saturationSeekbar) {
                //Converting value to float because of the filter function
                val progressInFloat: Float = .10f * progress
                saturationProgress = progressInFloat
                Filters.applyCustomFilters(
                    currentImage,
                    brightnessProgress,
                    contrastProgress,
                    saturationProgress,
                    originalImage,
                    filterSelected
                )
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