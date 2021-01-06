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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.ImageSetter
import com.example.lionheartapp.R
import com.example.lionheartapp.filters.Filters
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditImageFragment : Fragment(), View.OnClickListener {

    private val args by navArgs<EditImageFragmentArgs>()
    private lateinit var currentImage: ImageView

    //Filters
    private lateinit var noFilter: ImageView
    private lateinit var starLitFilter: ImageView
    private lateinit var blueMessFilter: ImageView
    private lateinit var aweStruckVibeFilter: ImageView
    private lateinit var limeStutterFilter: ImageView
    private lateinit var nightWhisperFilter: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_image, container, false)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        setHasOptionsMenu(true)

        currentImage = view.findViewById(R.id.currentPhoto)

        noFilter = view.findViewById(R.id.noFilter)
        starLitFilter = view.findViewById(R.id.starLitFilter)
        blueMessFilter = view.findViewById(R.id.blueMessFilter)
        aweStruckVibeFilter = view.findViewById(R.id.aweStruckVibeFilter)
        limeStutterFilter = view.findViewById(R.id.limeStutterFilter)
        nightWhisperFilter = view.findViewById(R.id.nightWhisperFilter)

        bindImages()
        filterSetup()
        noFilter.setOnClickListener(this)
        starLitFilter.setOnClickListener(this)
        blueMessFilter.setOnClickListener(this)
        aweStruckVibeFilter.setOnClickListener(this)
        limeStutterFilter.setOnClickListener(this)
        nightWhisperFilter.setOnClickListener(this)

        return view
    }

    private fun bindImages() {
        currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))

        noFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
        starLitFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
        blueMessFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
        aweStruckVibeFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
        limeStutterFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
        nightWhisperFilter.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
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
        val myDrawable = imageView.drawable
        val bitmap = (myDrawable as BitmapDrawable).bitmap
        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/*"
        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context))
        context.startActivity(Intent.createChooser(i, "Share Image"))
    }

    private fun getLocalBitmapUri(bmp: Bitmap, context: Context): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.noFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
            }
            R.id.starLitFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
                Filters.applyStarLitFilter(currentImage)
            }
            R.id.blueMessFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
                Filters.applyBlueMessFilter(currentImage)
            }
            R.id.aweStruckVibeFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
                Filters.applyAweStruckVibeFilter(currentImage)
            }
            R.id.limeStutterFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
                Filters.applyLimeStutterFilter(currentImage)
            }
            R.id.nightWhisperFilter -> {
                currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))
                Filters.applyNightWhisperFilter(currentImage)
            }
        }
    }

    private fun filterSetup() {
        Filters.applyStarLitFilter(starLitFilter)
        Filters.applyBlueMessFilter(blueMessFilter)
        Filters.applyAweStruckVibeFilter(aweStruckVibeFilter)
        Filters.applyLimeStutterFilter(limeStutterFilter)
        Filters.applyNightWhisperFilter(nightWhisperFilter)
    }

}