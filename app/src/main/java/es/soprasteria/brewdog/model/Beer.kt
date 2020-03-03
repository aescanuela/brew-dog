package es.soprasteria.brewdog.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Beer() : Parcelable {

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

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        name = parcel.readString()
        tagline = parcel.readString()
        firstBrewed = parcel.readString()
        description = parcel.readString()
        imageUrl = parcel.readString()
        abv = parcel.readValue(Double::class.java.classLoader) as? Double
        foodPairing = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(tagline)
        parcel.writeString(firstBrewed)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
        parcel.writeValue(abv)
        parcel.writeStringList(foodPairing)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Beer> {
        override fun createFromParcel(parcel: Parcel): Beer {
            return Beer(parcel)
        }

        override fun newArray(size: Int): Array<Beer?> {
            return arrayOfNulls(size)
        }
    }

}
