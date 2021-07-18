package com.example.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinlerkitabi.model.Besin
import com.example.besinlerkitabi.service.BesinAPIService
import com.example.besinlerkitabi.service.BesinDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application : Application): BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val besinApiService = BesinAPIService()
    private val disposable = CompositeDisposable()



    fun refreshData(){

        verileriInternettenAl()

    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        disposable.add(
            besinApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){

                    override fun onSuccess(t: List<Besin>) {
                        sqliteSakla(t)

                    }

                    override fun onError(e: Throwable) {
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()

                    }

                })
        )
    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinListesi: List<Besin>){

        launch{

            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())
            var i =0
            while(i<besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i=i+1

            }

            besinleriGoster(besinListesi)


        }


    }





}