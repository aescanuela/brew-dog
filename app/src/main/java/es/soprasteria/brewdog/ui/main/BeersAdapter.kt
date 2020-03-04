package es.soprasteria.brewdog.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.soprasteria.brewdog.R
import es.soprasteria.brewdog.constants.AppConstants
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.networking.DownloadImageTask
import java.util.*


/**
 * Adapter to show beer list
 */
class BeersAdapter(
    private val mContext: Context,
    private val mBeers: ArrayList<Beer>,
    private val listener: BeerAdapterListener
) :
    RecyclerView.Adapter<BeersAdapter.ViewHolder>() {


    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    // Save the last order that we used to order the list
    private var mLastOrder = AppConstants.Direction.ASCENDING

    /**
     * Listener to communicate with the Activity/Fragment that holds this Adapter
     */
    interface BeerAdapterListener {
        fun onBeerClicked(beer: Beer)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_beer, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mBeers.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val beer = mBeers[position]

        holder.nameTextView.text = beer.name
        holder.taglineTextView.text = beer.tagline
        holder.abvTextView.text =
            mContext.getString(R.string.abv_percentage, beer.abv ?: 0.0)

        // Show empty image while loading
        holder.iconImageView.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.notfound
            )
        )

        // Download image on an Async Task
        if (beer.imageUrl != null) {
            DownloadImageTask(holder.iconImageView, beer.imageUrl!!).execute()
        }

        // Call listener when an item is clicked
        holder.parentLayout.setOnClickListener {
            listener.onBeerClicked(beer)
        }
    }


    /**
     * Set list of beers for the adapter
     */
    fun setBeers(beerList: ArrayList<Beer>) {
        mBeers.clear()
        mBeers.addAll(beerList)
        order(mLastOrder)
    }


    /**
     * Set the order for the list
     * @param order if we want to order list ascending/descending
     */
    fun order(order: AppConstants.Direction) {
        mLastOrder = order
        if (order == AppConstants.Direction.ASCENDING) {
            mBeers.sortBy { it.abv }
        } else {
            mBeers.sortByDescending { it.abv }
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var parentLayout: ConstraintLayout = itemView.findViewById(R.id.beer_item_parent_layout)
        var iconImageView: ImageView = itemView.findViewById(R.id.beer_item_icon)
        var nameTextView: TextView = itemView.findViewById(R.id.beer_item_name)
        var taglineTextView: TextView = itemView.findViewById(R.id.beer_item_tagline)
        var abvTextView: TextView = itemView.findViewById(R.id.beer_item_abv)
    }

}

