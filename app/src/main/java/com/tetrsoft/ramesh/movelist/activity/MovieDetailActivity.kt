package com.tetrsoft.ramesh.movelist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tetrsoft.ramesh.movelist.R
import com.tetrsoft.ramesh.movelist.utils.AppConstants.Companion.INTENT_KEY
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        if(intent.hasExtra(INTENT_KEY)){
            movieDetailTv.text=intent.getStringExtra(INTENT_KEY)
        }

    }
}
