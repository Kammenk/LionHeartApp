package com.example.lionheartapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lionheartapp.R
import com.example.lionheartapp.models.PhotosItem
import com.example.lionheartapp.ui.fragments.PhotoListFragmentDirections
import com.squareup.picasso.Picasso

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    private var photoList = emptyList<PhotosItem>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.photo_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = photoList[position]

        val image = holder.itemView.findViewById<ImageView>(R.id.photoImage)
        Picasso.get().load(currentItem.urls.small).fit().centerInside().into(image)
        holder.itemView.findViewById<CardView>(R.id.photoItem).setOnClickListener {
            val action = PhotoListFragmentDirections.actionPhotoListFragmentToDetailFragment()
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setData(newData: ArrayList<PhotosItem>){
        val photosDiffUtil = PhotosDiffUtil(photoList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(photosDiffUtil)
        photoList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}