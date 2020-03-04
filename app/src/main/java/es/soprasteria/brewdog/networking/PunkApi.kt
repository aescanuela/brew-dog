package es.soprasteria.brewdog.networking

import es.soprasteria.brewdog.model.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Api calls
 */
interface PunkApi {

    /**
     * Get list of beers
     * @param food to return beers that pair with this food. Could be empty to get list of all beers.
     */
    @GET("beers")
    fun getBeers(
        @Query("food") food: String?
    ): Call<ArrayList<Beer>>

}
