package com.mdudhin.moviecatalogue.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow (
    var id: Int = 0,
    var poster_path: String? = null,
    var name: String? = null,
    var first_air_date: String? = null,
    var vote_average: Double = 0.0,
    var original_language: String? = null,
    var overview: String? = null,
    var backdrop_path: String? = null,
    var favorite: String? = "false"
) : Parcelable