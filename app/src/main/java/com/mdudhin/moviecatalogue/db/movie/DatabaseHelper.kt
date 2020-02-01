package com.mdudhin.moviecatalogue.db.movie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mdudhin.moviecatalogue.db.movie.DatabaseContract.MovieColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "dbfavmovie"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FAV_MOVIE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.MovieColumns._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.MovieColumns.POSTER_PATH} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.RELEASE_DATE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.VOTE_AVERAGE} REAL NOT NULL," +
                " ${DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.OVERVIEW} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.BACKDROP_PATH} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAV_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}