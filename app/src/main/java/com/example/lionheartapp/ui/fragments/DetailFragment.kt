package com.example.lionheartapp.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.R
import com.squareup.picasso.Picasso
import java.io.File


class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var detailImage: ImageView
    private lateinit var detailDesc: TextView
    private lateinit var detailCreatorName: TextView
    private lateinit var detailCreatorImage: ImageView
    private lateinit var detailLikes: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_detail, container, false)

        setHasOptionsMenu(true)

        detailImage = view.findViewById(R.id.detailedImage)
        detailDesc = view.findViewById(R.id.detailedDesc)
        detailCreatorName = view.findViewById(R.id.detailedCreatorName)
        detailCreatorImage = view.findViewById(R.id.detailedCreatorImage)
        detailLikes = view.findViewById(R.id.detailedLikes)
        Picasso.get().load(args.currentPhotoItem.urls.small).fit().centerInside().into(detailImage)
        detailDesc.text = args.currentPhotoItem.altDescription
        detailCreatorName.text = args.currentPhotoItem.user.name
        Picasso.get().load(args.currentPhotoItem.user.profileImage.small).fit().centerInside().into(
            detailCreatorImage
        )
        detailLikes.text = "${args.currentPhotoItem.likes} likes"


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit){
            val action = DetailFragmentDirections.actionDetailFragmentToEditImageFragment(args.currentPhotoItem)
            findNavController().navigate(action)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

}