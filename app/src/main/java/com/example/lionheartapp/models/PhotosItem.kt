package com.example.lionheartapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PhotosItem(
    @SerializedName("alt_description")
    val altDescription: String,
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("categories")
    val categories: @RawValue List<Any>,
    @SerializedName("color")
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_user_collections")
    val currentUserCollections: @RawValue List<Any>,
    @SerializedName("description")
    val description: @RawValue Any,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("promoted_at")
    val promotedAt: @RawValue Any,
    @SerializedName("sponsorship")
    val sponsorship:@RawValue Sponsorship,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls:@RawValue Urls,
    @SerializedName("user")
    val user:@RawValue User,
    @SerializedName("width")
    val width: Int
): Parcelable