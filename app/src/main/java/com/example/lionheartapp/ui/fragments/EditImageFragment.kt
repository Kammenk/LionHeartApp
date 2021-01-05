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
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.ImageSetter
import com.example.lionheartapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditImageFragment : Fragment() {

    private val args by navArgs<EditImageFragmentArgs>()
    private lateinit var currentImage: ImageView
    private lateinit var filterImageView: ImageView

    //Filters
    private lateinit var camFootageFilter: ImageView
    private lateinit var distortedFilter: ImageView
    private lateinit var hueFilter: ImageView
    private lateinit var instantFilter: ImageView
    private lateinit var pinkyFilter: ImageView


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
        filterImageView = view.findViewById(R.id.filterImage)

        camFootageFilter = view.findViewById(R.id.camFootageFilter)
        distortedFilter = view.findViewById(R.id.distortedFilter)
        hueFilter = view.findViewById(R.id.hueFilter)
        instantFilter = view.findViewById(R.id.instantFilter)
        pinkyFilter = view.findViewById(R.id.pinkyFilter)

        currentImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrl))

        camFootageFilter.setOnClickListener {
            filterImageView.setImageResource(R.drawable.cam_footage_filter)
        }

        distortedFilter.setOnClickListener {
            filterImageView.setImageResource(R.drawable.distorted_filter)
        }
        hueFilter.setOnClickListener {
            filterImageView.setImageResource(R.drawable.hue_filter)
        }
        instantFilter.setOnClickListener {
            filterImageView.setImageResource(R.drawable.instant_filter)
        }
        pinkyFilter.setOnClickListener {
            filterImageView.setImageResource(R.drawable.pinky_filter)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearFilters) {
            filterImageView.setImageResource(0)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

    //Used to share an image after its been filtered
    fun shareImage(context: Context, imageView: ImageView) {
        val myDrawable = imageView.drawable
        val bitmap = (myDrawable as BitmapDrawable).bitmap
        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/*"
        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context))
        context.startActivity(Intent.createChooser(i, "Share Image"))

    }

    fun getLocalBitmapUri(bmp: Bitmap, context: Context): Uri? {
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


}