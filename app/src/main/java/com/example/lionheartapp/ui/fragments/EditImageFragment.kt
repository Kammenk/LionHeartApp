package com.example.lionheartapp.ui.fragments

import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

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



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_image, container, false)

        setHasOptionsMenu(true)

        currentImage = view.findViewById(R.id.currentPhoto)
        filterImageView = view.findViewById(R.id.filterImage)

        camFootageFilter = view.findViewById(R.id.camFootageFilter)
        distortedFilter = view.findViewById(R.id.distortedFilter)
        hueFilter = view.findViewById(R.id.hueFilter)
        instantFilter = view.findViewById(R.id.instantFilter)
        pinkyFilter = view.findViewById(R.id.pinkyFilter)

        Picasso.get().load(args.currentItem.urls.regular).into(currentImage)

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
        inflater.inflate(R.menu.edit_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shareEdited) {
            var shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.type = "image/*"
                this.putExtra(Intent.EXTRA_SUBJECT,"")
                this.putExtra(Intent.EXTRA_TEXT,"")
            }
            startActivity(shareIntent)
        } else if(item.itemId == R.id.saveEdited){

        } else if(item.itemId == R.id.clearFilters){
            filterImageView.setImageResource(0)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

}