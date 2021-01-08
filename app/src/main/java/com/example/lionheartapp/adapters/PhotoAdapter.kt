package com.example.lionheartapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lionheartapp.ImageSetter
import com.example.lionheartapp.R
import com.example.lionheartapp.models.PhotoItem
import com.example.lionheartapp.ui.fragments.PhotoListFragmentDirections

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    private var photoList = emptyList<PhotoItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Used to inflate the item which will be used as a holder for each of our items
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.photo_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Binding the data we were provided to the items in the view
        val currentItem = photoList[position]
        val image = holder.itemView.findViewById<ImageView>(R.id.photoImage)
        println("current image ${currentItem.photoUrlSmall}")
        image.setImageBitmap(ImageSetter.setImage(currentItem.photoUrlSmall.toString()))
        holder.itemView.findViewById<CardView>(R.id.photoItem).setOnClickListener {
            val action =
                PhotoListFragmentDirections.actionPhotoListFragmentToDetailFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setData(newData: ArrayList<PhotoItem>) {
        //Used to add data to our recyclerview, we're using diff util as it loads only the necessary data
        val photosDiffUtil = PhotosDiffUtil(photoList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(photosDiffUtil)
        photoList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}