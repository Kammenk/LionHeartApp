package com.example.lionheartapp.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.lionheartapp.R
import java.io.File
import java.io.FileOutputStream


class SharableFragment : Fragment(), View.OnClickListener {

    private lateinit var sharableOne: ImageView
    private lateinit var sharableTwo: ImageView
    private lateinit var sharableThree: ImageView
    private lateinit var sharableFour: ImageView
    private lateinit var sharableFive: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sharable, container, false)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        sharableOne = view.findViewById(R.id.sharableImageOne)
        sharableTwo = view.findViewById(R.id.sharableImageTwo)
        sharableThree = view.findViewById(R.id.sharableImageThree)
        sharableFour = view.findViewById(R.id.sharableImageFour)
        sharableFive = view.findViewById(R.id.sharableImageFive)

        sharableOne.setOnClickListener(this)
        sharableTwo.setOnClickListener(this)
        sharableThree.setOnClickListener(this)
        sharableFour.setOnClickListener(this)
        sharableFive.setOnClickListener(this)

        return view
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.sharableImageOne -> {
                    shareImage("sharableone.jpg", sharableOne)
                }
                R.id.sharableImageTwo -> {
                    shareImage("sharabletwo.jpg", sharableTwo)
                }
                R.id.sharableImageThree -> {
                    shareImage("sharablethree.jpg", sharableThree)
                }
                R.id.sharableImageFour -> {
                    shareImage("sharablefour.jpg", sharableFour)
                }
                R.id.sharableImageFive -> {
                    shareImage("sharablefive.jpg", sharableFive)
                }
            }
        }
    }

    private fun shareImage(imagePath: String, imageView: ImageView){
        //share image
        try {
            val myDrawable = imageView.drawable
            val bitmap = (myDrawable as BitmapDrawable).bitmap
            val file = File(requireContext().externalCacheDir, imagePath)
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            //file.setReadable(true, false)
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/*"
            startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}