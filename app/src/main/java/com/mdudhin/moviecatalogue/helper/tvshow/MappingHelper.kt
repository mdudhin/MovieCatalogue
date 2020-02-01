package com.mdudhin.moviecatalogue.helper.tvshow

import android.database.Cursor
import com.mdudhin.moviecatalogue.db.tvshow.DatabaseContract.TvShowColumns
import com.mdudhin.moviecatalogue.entity.TvShow

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor): ArrayList<TvShow> {
        val notesList = ArrayList<TvShow>()
        notesCursor.moveToFirst()
        while (notesCursor.moveToNext()) {
            val id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(TvShowColumns._ID))
            val poster_path = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.POSTER_PATH))
            val name = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.NAME))
            val first_air_date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.FIRST_AIR_DATE))
            val vote_average = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(TvShowColumns.VOTE_AVERAGE))
            val original_language = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.ORIGINAL_LANGUAGE))
            val overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.OVERVIEW))
            val backdrop_path = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.BACKDROP_PATH))
            val favorite = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TvShowColumns.FAVORITE))
            notesList.add(TvShow(id, poster_path, name, first_air_date, vote_average, original_language, overview, backdrop_path, favorite))
        }
        return notesList
    }

}