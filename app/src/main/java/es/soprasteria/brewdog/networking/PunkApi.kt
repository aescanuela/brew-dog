package es.soprasteria.brewdog.networking

import es.soprasteria.brewdog.model.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApi {

    @GET("beers")
    fun getBeers(
        @Query("food") food: String?
    ): Call<ArrayList<Beer>>

}
