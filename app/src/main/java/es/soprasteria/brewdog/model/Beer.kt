package es.soprasteria.brewdog.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Beer {

    @SerializedName("id")
    @Expose
    var id: Long? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("tagline")
    @Expose
    var tagline: String? = null

    @SerializedName("first_brewed")
    @Expose
    var firstBrewed: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null

    @SerializedName("abv")
    @Expose
    var abv: Double? = null

    @SerializedName("food_pairing")
    @Expose
    var foodPairing: List<String>? = null

}
