package es.soprasteria.brewdog.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.soprasteria.brewdog.R
import es.soprasteria.brewdog.constants.AppConstants
import es.soprasteria.brewdog.model.Beer
import java.util.*


class BeersAdapter(
    private val mContext: Context,
    private val mBeers: ArrayList<Beer>
) :
    RecyclerView.Adapter<BeersAdapter.ViewHolder>() {


    private val TAG = BeersAdapter::class.java.simpleName


    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mLastOrder = AppConstants.ASCENDING


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.beer_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mBeers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beer = mBeers[position]
        holder.nameTextView.text = beer.name
        holder.taglineTextView.text = beer.tagline
        holder.descriptionTextView.text = beer.description
        holder.abvTextView.text =
            mContext.getString(R.string.abv_percentage, beer.abv ?: 0.0)

        // Show empty image while loading
        holder.iconImageView.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.notfound
            )
        )

        if (beer.imageUrl != null) {
            DownloadImageTask(holder.iconImageView, beer.imageUrl!!).execute()
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iconImageView: ImageView = itemView.findViewById(R.id.beer_item_icon)
        var nameTextView: TextView = itemView.findViewById(R.id.beer_item_name)
        var taglineTextView: TextView = itemView.findViewById(R.id.beer_item_tagline)
        var descriptionTextView: TextView = itemView.findViewById(R.id.beer_item_description)
        var abvTextView: TextView = itemView.findViewById(R.id.beer_item_abv)
    }


    fun setBeers(beerList: ArrayList<Beer>) {
        mBeers.clear()
        mBeers.addAll(beerList)
        order(mLastOrder)
    }

    fun order(order: Int) {
        mLastOrder = order
        if (order == AppConstants.ASCENDING) {
            mBeers.sortBy { it.abv }
        } else {
            mBeers.sortByDescending { it.abv }
        }
        notifyDataSetChanged()
    }


    private inner class DownloadImageTask(
        internal var imageView: ImageView,
        internal var urlToDisplay: String
    ) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            // FIXME: Guardar imagenes en disco?
            var bmp: Bitmap? = null
            try {
                val `in` = java.net.URL(urlToDisplay).openStream()
                bmp = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bmp
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            }
        }
    }


}

