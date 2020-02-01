package com.mdudhin.moviecatalogue.db.tvshow

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mdudhin.moviecatalogue.db.tvshow.DatabaseContract.TvShowColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "dbfavtvshow"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FAV_TV_SHOW = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.TvShowColumns._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.TvShowColumns.POSTER_PATH} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.FIRST_AIR_DATE} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.VOTE_AVERAGE} REAL NOT NULL," +
                " ${DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.OVERVIEW} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.BACKDROP_PATH} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAV_TV_SHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}