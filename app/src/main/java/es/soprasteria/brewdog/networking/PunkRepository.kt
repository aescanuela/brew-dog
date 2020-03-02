package es.soprasteria.brewdog.networking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.soprasteria.brewdog.MyApplication
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PunkRepository {

    private val punkApi: PunkApi = RetrofitService.cteateService(PunkApi::class.java)

    companion object {

        private val TAG = PunkRepository::class.java.simpleName

        private var punkRepository: PunkRepository? = null

        val instance: PunkRepository
            get() {
                if (punkRepository == null) {
                    punkRepository = PunkRepository()
                }
                return punkRepository!!
            }
    }

    // FIXME My alterations below:
    private val beerLiveData = MutableLiveData<ArrayList<Beer>>()


    fun getBeers(): MutableLiveData<ArrayList<Beer>> {
        return beerLiveData
    }


    fun fetchBeers(food: String?): MutableLiveData<ArrayList<Beer>> {

//        val beerResponse = MutableLiveData<ArrayList<Beer>>()

        val foodToSearch = if (food.isNullOrEmpty()) {
            "_empty_"
        } else {
            food
        }


        val gettPreff = PreferenceUtils.getStringPreference(
            MyApplication.instance,
            foodToSearch,
            null
        )

//        val dbResult = MyApplication.instance!!.getDatabase().searchDao().load(foodToSearch)
//        if (dbResult.value != null) {
        if (gettPreff != null) {

            try {
                val gson = Gson()
                val collectionType = object : TypeToken<Collection<Beer>>() {}.type
//                val resultInverse =
//                    gson.fromJson<ArrayList<Beer>>(dbResult.value?.result, collectionType)
                val resultInverse =
                    gson.fromJson<ArrayList<Beer>>(gettPreff, collectionType)

                beerLiveData.postValue(resultInverse)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {

            punkApi.getBeers(food)
                .enqueue(object : Callback<ArrayList<Beer>> {
                    override fun onResponse(
                        call: Call<ArrayList<Beer>>,
                        response: Response<ArrayList<Beer>>
                    ) {
                        if (response.isSuccessful) {
//                        beerResponse.setValue(response.body())

                            beerLiveData.postValue(response.body())


                            // Save to DB
                            val gson = Gson()
                            val prefKey = if (food.isNullOrBlank()) {
                                "_empty_"
                            } else {
                                food
                            }

                            // FIXME: --- saving to Prefs ---
//                            val gson = Gson()
//                            val prefKey = if (food.isNullOrBlank()) {
//                                "_empty_"
//                            } else {
//                                food
//                            }
                            PreferenceUtils.setStringPreference(
                                MyApplication.instance!!,
                                prefKey,
                                gson.toJson(response.body()).toString()
                            )

                            // FIXME: --- getting from Prefs ---
//                        val gettPreff = PreferenceUtils.getStringPreference(
//                            MyApplication.appContext,
//                            prefKey,
//                            null
//                        )
//                        val collectionType = object : TypeToken<Collection<Beer>>() {}.type
//                        val resultInverse = gson.fromJson<ArrayList<Beer>>(gettPreff, collectionType)

                        } else {
                            Log.e(
                                TAG,
                                "Error calling fetchBeers: " + response.code() + " " + response.message()
                            )
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Beer>>, t: Throwable) {
//                    beerResponse.value = null

                        beerLiveData.postValue(null)

                        t.printStackTrace()
                    }
                })
        }
        return beerLiveData
    }


}
