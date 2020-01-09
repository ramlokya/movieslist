package com.tetrsoft.ramesh.movelist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetrasoft.ramesh.movelist.ado.PopularDto
import com.tetrasoft.ramesh.movelist.network.ApiBaseConfig.Companion.KEY
import com.tetrasoft.ramesh.movelist.network.ApiConfig
import com.tetrasoft.ramesh.movelist.network.KotlinEvent
import com.tetrsoft.ramesh.movelist.utils.AppConstants.Companion.ERROR_LOADING
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection

class MostPopularViewModel : ViewModel() {

    var mostPopularLiveData = MutableLiveData<PopularDto>()
    private var compositeDisposable = CompositeDisposable()
    var eventLiveData = MutableLiveData<KotlinEvent>()

    fun disposeRequest() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun fetchMostPopular() {
        eventLiveData.value = KotlinEvent.Companion.LoadingEvent
        ApiConfig.getApiInstance().getPopularData(KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ popularResponse ->
                eventLiveData.value = KotlinEvent.Companion.CompletedEvent
                if(popularResponse.code()== HttpURLConnection.HTTP_OK) {
                    mostPopularLiveData.value = popularResponse.body()
                }else{
                    eventLiveData.value= KotlinEvent.Companion.FailedEvent(ERROR_LOADING)
                }
            }, {
                eventLiveData.value = KotlinEvent.Companion.CompletedEvent
                eventLiveData.value= KotlinEvent.Companion.FailedEvent(ERROR_LOADING)
            }).addTo(compositeDisposable)
    }
}