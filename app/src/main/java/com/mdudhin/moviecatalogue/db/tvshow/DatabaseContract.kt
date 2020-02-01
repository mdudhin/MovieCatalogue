package com.mdudhin.moviecatalogue.db.tvshow

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class TvShowColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_tv_show"
            const val _ID = "_id"
            const val POSTER_PATH = "poster_path"
            const val NAME = "name"
            const val FIRST_AIR_DATE = "first_air_date"
            const val VOTE_AVERAGE = "vote_average"
            const val ORIGINAL_LANGUAGE = "original_language"
            const val OVERVIEW = "overview"
            const val BACKDROP_PATH = "backdrop_path"
            const val FAVORITE = "favorite"
        }
    }

}