package com.example.lionheartapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.example.lionheartapp.R
import com.example.lionheartapp.models.PhotoPost
import com.example.lionheartapp.models.PhotosItem
import com.example.lionheartapp.ui.fragments.PhotoListFragmentDirections
import com.example.lionheartapp.ui.fragments.TopPicksFragmentDirections
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.ArrayList

class ViewPagerAdapter(
        aList: ArrayList<PhotosItem>,
        context: Context
): PagerAdapter() {

    private var postList = aList
    private var context = context
    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return postList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.view_pager_item,container,false)

        var postImage = view.findViewById<ImageView>(R.id.viewPagerImage)
        var postDescription = view.findViewById<TextView>(R.id.viewPagerDesc)
        var postCreator = view.findViewById<TextView>(R.id.viewPagerCreator)
        var postCreatorImage = view.findViewById<ImageView>(R.id.viewPagerCreatorImage)

        Picasso.get().load(postList[position].urls.small).fit().centerInside().into(postImage)
        postDescription.text = postList[position].altDescription
        postCreator.text = postList[position].user.name
        Picasso.get().load(postList[position].user.profileImage.small).fit().centerInside().into(postCreatorImage)

        container.addView(view,0)

        view.setOnClickListener {
            val action = TopPicksFragmentDirections.actionTopPicksFragmentToDetailFragment(postList[position])
            view.findNavController().navigate(action)

        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}