package com.example.bookspdm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book (
    val title: String = "",
    val isbn: String = "",
    val firstAuthor: String = "",
    val publisher: String = "",
    val edition: Int = -1,
    val pages: Int = -1
): Parcelable