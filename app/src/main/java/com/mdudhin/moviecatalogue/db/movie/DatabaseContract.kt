package com.mdudhin.moviecatalogue.db.movie

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val POSTER_PATH = "poster_path"
            const val TITLE = "title"
            const val RELEASE_DATE = "release_date"
            const val VOTE_AVERAGE = "vote_average"
            const val ORIGINAL_LANGUAGE = "original_language"
            const val OVERVIEW = "overview"
            const val BACKDROP_PATH = "backdrop_path"
            const val FAVORITE = "favorite"
        }
    }
}