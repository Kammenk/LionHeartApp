package com.example.lionheartapp.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.example.lionheartapp.R
import com.example.lionheartapp.models.PhotoItem
import com.example.lionheartapp.ui.fragments.TopPicksFragmentDirections
import java.io.InputStream
import java.net.URL
import java.util.ArrayList

class ViewPagerAdapter(
    aList: ArrayList<PhotoItem>,
    context: Context
) : PagerAdapter() {

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
        val view = layoutInflater.inflate(R.layout.view_pager_item, container, false)

        var postImage = view.findViewById<ImageView>(R.id.viewPagerImage)
        var postCreator = view.findViewById<TextView>(R.id.viewPagerCreator)
        var postCreatorImage = view.findViewById<ImageView>(R.id.viewPagerCreatorImage)
        val photoInputStream: InputStream
        val creatorInputStream: InputStream
        val photoBitmap: Bitmap
        val creatorBitmap: Bitmap


        photoInputStream = URL(postList[position].photoUrl).openStream()
        photoBitmap = BitmapFactory.decodeStream(photoInputStream)
        postImage.setImageBitmap(photoBitmap)
        postCreator.text = postList[position].photoCreatorName
        creatorInputStream = URL(postList[position].photoCreatorImage).openStream()
        creatorBitmap = BitmapFactory.decodeStream(creatorInputStream)
        postCreatorImage.setImageBitmap(creatorBitmap)
        container.addView(view, 0)

        view.setOnClickListener {
            val action =
                TopPicksFragmentDirections.actionTopPicksFragmentToDetailFragment(postList[position])
            view.findNavController().navigate(action)

        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}