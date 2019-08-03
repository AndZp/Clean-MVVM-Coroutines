package io.mateam.playground.presentation.details.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewUiModel(
    val id: String,
    val author: String,
    val content: String,
    val url: String
) : Parcelable