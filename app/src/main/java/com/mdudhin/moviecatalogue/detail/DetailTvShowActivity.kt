package com.mdudhin.moviecatalogue.detail

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.mdudhin.moviecatalogue.R
import com.mdudhin.moviecatalogue.db.tvshow.DatabaseContract
import com.mdudhin.moviecatalogue.db.tvshow.FavTvShowHelper
import com.mdudhin.moviecatalogue.entity.TvShow
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import kotlinx.android.synthetic.main.activity_detail_tv_show.btn_fav
import kotlinx.android.synthetic.main.activity_detail_tv_show.progressBar
import kotlinx.android.synthetic.main.activity_detail_tv_show.rb_vote_average
import kotlinx.android.synthetic.main.activity_detail_tv_show.tv_language
import kotlinx.android.synthetic.main.activity_detail_tv_show.tv_overview
import kotlinx.android.synthetic.main.activity_detail_tv_show.tv_release_date
import kotlinx.android.synthetic.main.activity_detail_tv_show.tv_vote_average
import kotlinx.coroutines.*

class DetailTvShowActivity : AppCompatActivity(), OnLikeListener {

    private val posterSize = "w342"
    private var isEdit = false
    private var tvShow: TvShow? = null
    private var position: Int = 0
    private lateinit var favTvShowHelper: FavTvShowHelper

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
        const val EXTRA_POSITION = "extra_position"
        private const val LOG_ASYNC = "Async"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)

        favTvShowHelper = FavTvShowHelper.getInstance(applicationContext)

        tvShow = intent.getParcelableExtra(EXTRA_TV_SHOW)

        if (tvShow?.favorite == "true") {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            isEdit = false
        }

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.detail_tv_show)

        showLoading(true)

        GlobalScope.launch(Dispatchers.IO) {
            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                delay(600)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    val rating = tvShow?.vote_average?.div(2)
                    val vote = "${tvShow?.vote_average}/10"

                    tv_show_title.text = tvShow?.name
                    tv_release_date.text = tvShow?.first_air_date
                    rb_vote_average.rating = rating?.toFloat() ?: 0f
                    tv_vote_average.text = vote
                    tv_show_language.text = tvShow?.original_language
                    tv_show_overview.text = tvShow?.overview
                    Glide.with(this@DetailTvShowActivity)
                        .load("https://image.tmdb.org/t/p/$posterSize/${tvShow?.poster_path}")
                        .into(poster_tv_show)
                    Glide.with(this@DetailTvShowActivity)
                        .load("https://image.tmdb.org/t/p/$posterSize/${tvShow?.backdrop_path}")
                        .into(backdrop_tv_show)
                    btn_fav.isLiked = tvShow?.favorite?.toBoolean() ?: false
                }
            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
        }

        favTvShowHelper = FavTvShowHelper.getInstance(applicationContext)
        favTvShowHelper.open()

        btn_fav.setOnLikeListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(EXTRA_TV_SHOW) as String
            tv_show_title.text = result
            tv_release_date.text = result
            tv_vote_average.text = result
            tv_show_language.text = result
            tv_show_overview.text = result
            Glide.with(this@DetailTvShowActivity)
                .load(result)
                .into(poster_tv_show)
            Glide.with(this@DetailTvShowActivity)
                .load(result)
                .into(backdrop_tv_show)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_TV_SHOW, tv_show_title.text.toString())
        outState.putString(EXTRA_TV_SHOW, tv_release_date.text.toString())
        outState.putString(EXTRA_TV_SHOW, rb_vote_average.toString())
        outState.putString(EXTRA_TV_SHOW, tv_vote_average.text.toString())
        outState.putString(EXTRA_TV_SHOW, tv_show_language.text.toString())
        outState.putString(EXTRA_TV_SHOW, tv_show_overview.text.toString())
        outState.putString(EXTRA_TV_SHOW, poster_tv_show.toString())
        outState.putString(EXTRA_TV_SHOW, backdrop_tv_show.toString())
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            tv_show_title.visibility = View.GONE
            tv_release_date.visibility = View.GONE
            rb_vote_average.visibility = View.GONE
            tv_vote_average.visibility = View.GONE
            tv_language.visibility = View.GONE
            tv_show_language.visibility = View.GONE
            tv_overview.visibility = View.GONE
            tv_show_overview.visibility = View.GONE
            poster_tv_show.visibility = View.GONE
            backdrop_tv_show.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            tv_show_title.visibility = View.VISIBLE
            tv_release_date.visibility = View.VISIBLE
            rb_vote_average.visibility = View.VISIBLE
            tv_vote_average.visibility = View.VISIBLE
            tv_language.visibility = View.VISIBLE
            tv_show_language.visibility = View.VISIBLE
            tv_overview.visibility = View.VISIBLE
            tv_show_overview.visibility = View.VISIBLE
            poster_tv_show.visibility = View.VISIBLE
            backdrop_tv_show.visibility = View.VISIBLE
        }
    }

    override fun liked(likeButton: LikeButton?) {
        val values = ContentValues()
        values.put(DatabaseContract.TvShowColumns._ID, tvShow?.id)
        values.put(DatabaseContract.TvShowColumns.POSTER_PATH, tvShow?.poster_path)
        values.put(DatabaseContract.TvShowColumns.NAME, tvShow?.name)
        values.put(DatabaseContract.TvShowColumns.FIRST_AIR_DATE, tvShow?.first_air_date)
        values.put(DatabaseContract.TvShowColumns.VOTE_AVERAGE, tvShow?.vote_average)
        values.put(DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE, tvShow?.original_language)
        values.put(DatabaseContract.TvShowColumns.OVERVIEW, tvShow?.overview)
        values.put(DatabaseContract.TvShowColumns.BACKDROP_PATH, tvShow?.backdrop_path)
        values.put(DatabaseContract.TvShowColumns.FAVORITE, "true")

        if (!isEdit) {
            val result = favTvShowHelper.insert(values)
            if (result > 0) {
                Toast.makeText(this, "Berhasil menambah data ${tvShow?.name}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal menambah, ${tvShow?.name} telah di favorit", Toast.LENGTH_SHORT).show()
            }
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
                val intent = Intent()
                intent.putExtra(EXTRA_TV_SHOW, tvShow)
                intent.putExtra(EXTRA_POSITION, position)

                val values = ContentValues()
                values.put(DatabaseContract.TvShowColumns._ID, tvShow?.id)
                values.put(DatabaseContract.TvShowColumns.POSTER_PATH, tvShow?.poster_path)
                values.put(DatabaseContract.TvShowColumns.NAME, tvShow?.name)
                values.put(DatabaseContract.TvShowColumns.FIRST_AIR_DATE, tvShow?.first_air_date)
                values.put(DatabaseContract.TvShowColumns.VOTE_AVERAGE, tvShow?.vote_average)
                values.put(DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE, tvShow?.original_language)
                values.put(DatabaseContract.TvShowColumns.OVERVIEW, tvShow?.overview)
                values.put(DatabaseContract.TvShowColumns.BACKDROP_PATH, tvShow?.backdrop_path)
                values.put(DatabaseContract.TvShowColumns.FAVORITE, "true")

                val result = favTvShowHelper.insert(values)
                if (result > 0) {
                    Toast.makeText(this, "Berhasil menambah data ${tvShow?.name}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gagal menambah, ${tvShow?.name} telah di favorit", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {

        val dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
        val dialogTitle= "Hapus Tv Show"

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                val result = favTvShowHelper.deleteById(tvShow?.id.toString()).toLong()
                if (result > 0) {
                    finish()
                } else {
                    Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun unLiked(likeButton: LikeButton?) {
        showAlertDialog()
    }

}
