package com.tetrsoft.ramesh.movelist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tetrasoft.ramesh.movelist.network.KotlinEvent
import com.tetrsoft.ramesh.movelist.R
import com.tetrsoft.ramesh.movelist.adapter.MostPopularAdapter
import com.tetrsoft.ramesh.movelist.utils.AppConstants
import com.tetrsoft.ramesh.movelist.utils.AppConstants.Companion.INTENT_KEY
import com.tetrsoft.ramesh.movelist.utils.NetworkAvailability
import com.tetrsoft.ramesh.movelist.viewmodel.MostPopularViewModel
import kotlinx.android.synthetic.main.activity_most_popular.*

class MostPopularActivity : AppCompatActivity() {

    private var mViewModel: MostPopularViewModel? = null
    private var mostPopularAdapter: MostPopularAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_most_popular)

        initViewModel()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MostPopularViewModel::class.java)
        mViewModel?.let { viewModel ->
            if (NetworkAvailability.isNetworkAvailable(this)) {
                viewModel.fetchMostPopular()
            } else {
                showNoInternet()
            }
            viewModelListener()
        }
    }

    private fun viewModelListener() {
        mViewModel?.eventLiveData?.observe(this, Observer { event ->
            event?.let {
                when (event) {
                    is KotlinEvent.Companion.LoadingEvent -> {
                        showProgress()
                    }
                    is KotlinEvent.Companion.CompletedEvent -> {
                        hideProgress()
                    }
                    is KotlinEvent.Companion.FailedEvent -> {
                        showErrorMsg(event.error)
                    }
                }
            }
        })

        mViewModel?.mostPopularLiveData?.observe(this, Observer { mostPopular ->
            layoutManager = LinearLayoutManager(this)
            mMostPopularRecyclerView.setHasFixedSize(false)
            mostPopularAdapter = MostPopularAdapter(this, mostPopular.results) { result ->
                startActivity(
                    Intent(
                        this@MostPopularActivity,
                        MovieDetailActivity::class.java
                    ).putExtra(INTENT_KEY, result.title ?: "")
                )
            }
            mMostPopularRecyclerView.layoutManager = layoutManager
            mMostPopularRecyclerView.adapter = mostPopularAdapter
        })
    }

    private fun showProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        mProgressBar.visibility = View.INVISIBLE
    }

    private fun showErrorMsg(error: String?) {
        errorTv.visibility = View.VISIBLE
        errorTv.text = error
    }

    private fun showNoInternet() {
        errorTv.visibility = View.VISIBLE
        errorTv.text = AppConstants.INTERNET_MSG
    }

    override fun onDestroy() {
        mViewModel?.disposeRequest()
        super.onDestroy()
    }
}

