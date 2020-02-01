package com.mdudhin.moviecatalogue.helper.movie

import android.database.Cursor
import com.mdudhin.moviecatalogue.db.movie.DatabaseContract
import com.mdudhin.moviecatalogue.entity.Movie

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor): ArrayList<Movie> {
        val notesList = ArrayList<Movie>()
        notesCursor.moveToFirst()
        while (notesCursor.moveToNext()) {
            val id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
            val poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH))
            val title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
            val release = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE))
            val vote = notesCursor.getDouble(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE))
            val language = notesCursor.getString(notesCursor.getColumnIndexOrThrow(
                DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE))
            val overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(
                DatabaseContract.MovieColumns.OVERVIEW))
            val backdrop = notesCursor.getString(notesCursor.getColumnIndexOrThrow(
                DatabaseContract.MovieColumns.BACKDROP_PATH))
            val favorite = notesCursor.getString(notesCursor.getColumnIndexOrThrow(
                DatabaseContract.MovieColumns.FAVORITE))
            notesList.add(Movie(id, poster, title, release, vote, language, overview, backdrop, favorite))
        }
        return notesList
    }

}