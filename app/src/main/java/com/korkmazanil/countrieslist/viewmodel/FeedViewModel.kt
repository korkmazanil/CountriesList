package com.korkmazanil.countrieslist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.korkmazanil.countrieslist.model.Country
import com.korkmazanil.countrieslist.service.CountryAPIService
import com.korkmazanil.countrieslist.service.CountryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

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
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList : List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list : List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            var listLong = dao.insertAll(*list.toTypedArray()) // list -> individual

            var i = 0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(list)
        }
    }


}