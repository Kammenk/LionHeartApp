package com.example.lionheartapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoItem(
    val photoCreatorName: String,
    val photoCreatorImage: String,
    val photoDescription: String,
    val photoLikes: Int,
    val photoUrl: String
): Parcelable