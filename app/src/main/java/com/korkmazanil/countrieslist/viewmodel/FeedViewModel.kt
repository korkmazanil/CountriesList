package com.korkmazanil.countrieslist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.korkmazanil.countrieslist.model.Country
import com.korkmazanil.countrieslist.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refleshData(){

//        val country = Country("Turkey","Asia","Ankara","TRY","Turkish","")
//        val country2 = Country("France","Europe","Paris","EUR","French","")
//        val country3 = Country("Germany","Europe","Berlin","EUR","German","")
//        val country4 = Country("England","Europe","London","GBP","English","")
//
//        val countryList = arrayListOf<Country>(country,country2,country3,country4)
//        countries.value = countryList
//        countryError.value = false
//        countryLoading.value = false

        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }


}