package es.soprasteria.brewdog.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.networking.PunkRepository


/**
 * ViewModel to manage the data for first screen (MainActivity + MainFragment)
 */
class MainViewModel : ViewModel() {

    private var punkRepository: PunkRepository? = null

    private var beerMutableLiveData: MutableLiveData<ArrayList<Beer>>? = null


    fun init() {
        if (beerMutableLiveData != null) {
            return
        }
        punkRepository = PunkRepository.instance

        // Observe changes on beers
        beerMutableLiveData = punkRepository!!.getBeers()

        // Do first call - null will not send the GET parameter, so it is safe
         punkRepository!!.fetchBeers(null)
    }


    /**
     * Get LiveData to observe
     */
    fun getBeersRepository(): LiveData<ArrayList<Beer>>? {
        return beerMutableLiveData
    }


    /**
     * Refresh list of beers
     * @param food: the food to query beers
     */
    fun refreshBeers(food: String?) {
            punkRepository!!.fetchBeers(if (food == null || food.isEmpty()) null else food)
    }


}
