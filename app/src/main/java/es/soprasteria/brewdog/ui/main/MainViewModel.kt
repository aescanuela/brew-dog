package es.soprasteria.brewdog.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.networking.PunkRepository


class MainViewModel : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName


    private var punkRepository: PunkRepository? = null

    //    private var beerMutableLiveData: MutableLiveData<ArrayList<Beer>>? = null
    private var beerMutableLiveData: MutableLiveData<ArrayList<Beer>>? = null


    fun init() {
        if (beerMutableLiveData != null) {
            return
        }
        punkRepository = PunkRepository.instance

        // TODO: Pagination
//        beerMutableLiveData = punkRepository!!.fetchBeers(null)

        // Observe only getBeers
        beerMutableLiveData = punkRepository!!.getBeers()

        // Do first call - null will not send the GET parameter
         punkRepository!!.fetchBeers(null)


    }


    fun getBeersRepository(): LiveData<ArrayList<Beer>>? {
        return beerMutableLiveData
    }


    fun refreshBeers(food: String?) {
      /*  beerMutableLiveData = */
            punkRepository!!.fetchBeers(if (food == null || food.isEmpty()) null else food)

//        punkRepository!!.getBeers(if (food == null || food.isEmpty()) null else food)
//
//        beerMutableLiveData!!.postValue( punkRepository!!.getBeers(if (food == null || food.isEmpty()) null else food))

        Log.d(TAG, "Searched for " + food + " and found " + beerMutableLiveData?.value?.size)
    }


}
