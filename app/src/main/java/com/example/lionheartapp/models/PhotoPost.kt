package com.example.lionheartapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoPost(
    val postImage: Int,
    val postDescription: String,
    val postCreator: String,
    val postCreatorImage: Int
): Parcelable