package es.soprasteria.brewdog.networking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository to access to beers via Punk API
 */
class PunkRepository {

    private val TAG = PunkRepository::class.java.simpleName

    private val punkApi: PunkApi = RetrofitService.cteateService(PunkApi::class.java)

    private val beerLiveData = MutableLiveData<ArrayList<Beer>>()

    companion object {

        private var punkRepository: PunkRepository? = null

        // Singleton pattern
        val instance: PunkRepository
            get() {
                if (punkRepository == null) {
                    punkRepository = PunkRepository()
                }
                return punkRepository!!
            }
    }


    /**
     * Get LiveData to query beers
     */
    fun getBeers(): MutableLiveData<ArrayList<Beer>> {
        return beerLiveData
    }


    /**
     * Update list of beers, searching by matching food
     * @param food to match beers
     * @return LiveData to observe for beers
     */
    fun fetchBeers(food: String?): MutableLiveData<ArrayList<Beer>> {

        val resultInverse = Utils.parseSearchJson(food)
        if (resultInverse != null) {

            // We have done this search before and have a saved result
            beerLiveData.postValue(resultInverse)

        } else {

            // We don't have saved results and have to query the API
            punkApi.getBeers(food)
                .enqueue(object : Callback<ArrayList<Beer>> {
                    override fun onResponse(
                        call: Call<ArrayList<Beer>>,
                        response: Response<ArrayList<Beer>>
                    ) {
                        if (response.isSuccessful) {

                            // Post the result to liveData
                            beerLiveData.postValue(response.body())

                            // Save in Preferences
                            Utils.saveSearchResults(food, response.body())

                        } else {
                            Log.e(
                                TAG,
                                "Error calling fetchBeers: " + response.code() + " " + response.message()
                            )
                            // Post the result to LiveData
                            beerLiveData.postValue(null)
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Beer>>, t: Throwable) {
                        // Post the result to LiveData
                        beerLiveData.postValue(null)
                        t.printStackTrace()
                    }
                })
        }
        return beerLiveData
    }


}
