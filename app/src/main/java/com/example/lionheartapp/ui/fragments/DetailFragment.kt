package com.example.lionheartapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lionheartapp.ImageSetter
import com.example.lionheartapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : Fragment() {

    //Variables
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var detailImage: ImageView
    private lateinit var detailDesc: TextView
    private lateinit var detailCreatorName: TextView
    private lateinit var detailCreatorImage: ImageView
    private lateinit var detailLikes: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        //Used when getting info from api
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        //Hiding bottom nav
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.GONE
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Enabling top menu
        setHasOptionsMenu(true)

        //Variable initialization
        detailImage = view.findViewById(R.id.detailedImage)
        detailDesc = view.findViewById(R.id.detailedDesc)
        detailCreatorName = view.findViewById(R.id.detailedCreatorName)
        detailCreatorImage = view.findViewById(R.id.detailedCreatorImage)
        detailLikes = view.findViewById(R.id.detailedLikes)

        //Binding data to the items in the view
        detailImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoUrlRegular))
        detailDesc.text =
            if (args.currentItem.photoDescription == "null") "The creator is yet to add a meaningful description" else args.currentItem.photoDescription
        detailCreatorName.text = args.currentItem.photoCreatorName
        detailCreatorImage.setImageBitmap(ImageSetter.setImage(args.currentItem.photoCreatorImage))
        detailLikes.text = "${args.currentItem.photoLikes} likes"
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Moving to edit fragment
        if (item.itemId == R.id.edit) {
            val action =
                DetailFragmentDirections.actionDetailFragmentToEditImageFragment(args.currentItem)
            findNavController().navigate(action)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }
}