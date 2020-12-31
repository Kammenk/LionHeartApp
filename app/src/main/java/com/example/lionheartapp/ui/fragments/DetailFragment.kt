package com.example.lionheartapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.lionheartapp.R
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var detailImage: ImageView
    private lateinit var detailDesc: TextView
    private lateinit var detailCreatorName: TextView
    private lateinit var detailCreatorImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_detail, container, false)

        setHasOptionsMenu(true)
        detailImage = view.findViewById(R.id.detailedImage)
        detailDesc = view.findViewById(R.id.detailedDescription)
        detailCreatorName = view.findViewById(R.id.detailedCreator)
        detailCreatorImage = view.findViewById(R.id.detailedCreatorImage)
        Picasso.get().load(args.currentPhotoItem.urls.small).fit().centerInside().into(detailImage)
        detailDesc.text = args.currentPhotoItem.altDescription
        detailCreatorName.text = args.currentPhotoItem.user.name
        Picasso.get().load(args.currentPhotoItem.user.profileImage.small).fit().centerInside().into(detailCreatorImage)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share){
            var shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,"Share")
                this.type = "plain/text"
            }
            startActivity(shareIntent)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

}