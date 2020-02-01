package com.mdudhin.moviecatalogue.detail

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.db.movie.DatabaseContract
import com.mdudhin.moviecatalogue.db.movie.FavMovieHelper
import com.mdudhin.moviecatalogue.entity.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.coroutines.*

class DetailMovieActivity : AppCompatActivity(), OnLikeListener {

    private val posterSize = "w342"

    private var isEdit = false
    private var movie: Movie? = null
    private var position: Int = 0
    private lateinit var favMovieHelper: FavMovieHelper

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION = "extra_position"
        private const val LOG_ASYNC = "Async"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        favMovieHelper = FavMovieHelper.getInstance(applicationContext)

        movie = intent.getParcelableExtra(EXTRA_MOVIE)

        if (movie?.favorite == "true") {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            isEdit = false
        }

        showLoading(true)
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                delay(600)

                withContext(Dispatchers.Main) {

                    showLoading(false)
                    val rating = movie?.vote_average?.div(2)
                    val vote = "${movie?.vote_average}/10"

                    tv_movie_title.text = movie?.title
                    tv_release_date.text = movie?.release_date
                    rb_vote_average.rating = rating?.toFloat() ?: 0f
                    tv_vote_average.text = vote
                    tv_movie_language.text = movie?.original_language
                    tv_movie_overview.text = movie?.overview
                    Glide.with(this@DetailMovieActivity)
                        .load("https://image.tmdb.org/t/p/$posterSize/${movie?.poster_path}")
                        .into(poster_movie)
                    Glide.with(this@DetailMovieActivity)
                        .load("https://image.tmdb.org/t/p/$posterSize/${movie?.backdrop_path}")
                        .into(backdrop_movie)
                    btn_fav.isLiked = movie?.favorite?.toBoolean() ?: false
                }
            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
        }

        favMovieHelper = FavMovieHelper.getInstance(applicationContext)
        favMovieHelper.open()

        btn_fav.setOnLikeListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(EXTRA_MOVIE) as String
            tv_movie_title.text = result
            tv_release_date.text = result
            tv_vote_average.text = result
            tv_movie_language.text = result
            tv_movie_overview.text = result
            Glide.with(this@DetailMovieActivity)
                .load(result)
                .into(poster_movie)
            Glide.with(this@DetailMovieActivity)
                .load(result)
                .into(backdrop_movie)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_MOVIE, tv_movie_title.text.toString())
        outState.putString(EXTRA_MOVIE, tv_release_date.text.toString())
        outState.putString(EXTRA_MOVIE, rb_vote_average.toString())
        outState.putString(EXTRA_MOVIE, tv_vote_average.text.toString())
        outState.putString(EXTRA_MOVIE, tv_movie_language.text.toString())
        outState.putString(EXTRA_MOVIE, tv_movie_overview.text.toString())
        outState.putString(EXTRA_MOVIE, poster_movie.toString())
        outState.putString(EXTRA_MOVIE, backdrop_movie.toString())
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            tv_movie_title.visibility = View.GONE
            tv_release_date.visibility = View.GONE
            rb_vote_average.visibility = View.GONE
            tv_vote_average.visibility = View.GONE
            tv_language.visibility = View.GONE
            tv_movie_language.visibility = View.GONE
            tv_overview.visibility = View.GONE
            tv_movie_overview.visibility = View.GONE
            poster_movie.visibility = View.GONE
            backdrop_movie.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            tv_movie_title.visibility = View.VISIBLE
            tv_release_date.visibility = View.VISIBLE
            rb_vote_average.visibility = View.VISIBLE
            tv_vote_average.visibility = View.VISIBLE
            tv_language.visibility = View.VISIBLE
            tv_movie_language.visibility = View.VISIBLE
            tv_overview.visibility = View.VISIBLE
            tv_movie_overview.visibility = View.VISIBLE
            poster_movie.visibility = View.VISIBLE
            backdrop_movie.visibility = View.VISIBLE
        }
    }

    override fun liked(likeButton: LikeButton?) {

        val intent = Intent()
        intent.putExtra(EXTRA_MOVIE, movie)
        intent.putExtra(EXTRA_POSITION, position)

        val values = ContentValues()
        values.put(DatabaseContract.MovieColumns._ID, movie?.id)
        values.put(DatabaseContract.MovieColumns.POSTER_PATH, movie?.poster_path)
        values.put(DatabaseContract.MovieColumns.TITLE, movie?.title)
        values.put(DatabaseContract.MovieColumns.RELEASE_DATE, movie?.release_date)
        values.put(DatabaseContract.MovieColumns.VOTE_AVERAGE, movie?.vote_average)
        values.put(DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE, movie?.original_language)
        values.put(DatabaseContract.MovieColumns.OVERVIEW, movie?.overview)
        values.put(DatabaseContract.MovieColumns.BACKDROP_PATH, movie?.backdrop_path)
        values.put(DatabaseContract.MovieColumns.FAVORITE, "true")

        val result = favMovieHelper.insert(values)
        if (result > 0) {
            Toast.makeText(this@DetailMovieActivity, "Berhasil menambah ${movie?.title}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@DetailMovieActivity, "Gagal menambah, ${movie?.title} telah di favorit", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        } else {
            menuInflater.inflate(R.menu.menu_add, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog()
            R.id.action_add -> {
                val values = ContentValues()
                values.put(DatabaseContract.MovieColumns._ID, movie?.id)
                values.put(DatabaseContract.MovieColumns.POSTER_PATH, movie?.poster_path)
                values.put(DatabaseContract.MovieColumns.TITLE, movie?.title)
                values.put(DatabaseContract.MovieColumns.RELEASE_DATE, movie?.release_date)
                values.put(DatabaseContract.MovieColumns.VOTE_AVERAGE, movie?.vote_average)
                values.put(DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE, movie?.original_language)
                values.put(DatabaseContract.MovieColumns.OVERVIEW, movie?.overview)
                values.put(DatabaseContract.MovieColumns.BACKDROP_PATH, movie?.backdrop_path)
                values.put(DatabaseContract.MovieColumns.FAVORITE, "true")

                val result = favMovieHelper.insert(values)
                if (result > 0) {
                    Toast.makeText(this@DetailMovieActivity, "Berhasil menambah ${movie?.title}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetailMovieActivity, "Gagal menambah, ${movie?.title} telah di favorit", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {

        val dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
        val dialogTitle= "Hapus Movie"

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                val result = favMovieHelper.deleteById(movie?.id.toString()).toLong()
                if (result > 0) {
                    finish()
                } else {
                    Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun unLiked(likeButton: LikeButton?) {
        showAlertDialog()
    }
}
