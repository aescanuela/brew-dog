package es.soprasteria.brewdog.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.inmovens.selyco.ui.adapters.spacing.SpacesItemDecorationVerticalDivider
import es.soprasteria.brewdog.R
import es.soprasteria.brewdog.constants.AppConstants
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.ui.detail.DetailActivity
import es.soprasteria.brewdog.ui.spacing.SpacesItemDecorationVertical
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private val TAG = MainFragment::class.java.simpleName

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var beersAdapter: BeersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Initialize viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init()

        // Initialize call for all beers (TODO: Should paginate first time?)
        viewModel.getBeersRepository()!!.observe(viewLifecycleOwner, Observer { data ->
            try {
                Log.d(TAG, "Received data: ${data.size}")

                if (data.isNullOrEmpty()) {
                    main_fragment_beer_recycler.visibility = View.GONE
                    main_fragment_empty_message.visibility = View.VISIBLE

                } else {
                    main_fragment_beer_recycler.visibility = View.VISIBLE
                    main_fragment_empty_message.visibility = View.GONE

                    beersAdapter.setBeers(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        // Prepare Adapter
        if (context != null) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = RecyclerView.VERTICAL
            main_fragment_beer_recycler.layoutManager = layoutManager



            beersAdapter = BeersAdapter(
                context!!,
                arrayListOf(),
                listener = object : BeersAdapter.BeerAdapterListener {
                    override fun onBeerClicked(beer: Beer) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(AppConstants.INTENT_EXTRA_BEER, beer)
                        startActivity(intent)
                    }
                })

            main_fragment_beer_recycler.adapter = beersAdapter

            // Decorations

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.padding_medium)
            val topBottomSpace = resources.getDimensionPixelSize(R.dimen.margin_small)
            val leftRightSpace = resources.getDimensionPixelSize(R.dimen.margin_small)

            val dividerItemDecoration2 = SpacesItemDecorationVertical(
                topBottomSpace,
                spacingInPixels * 2,
                leftRightSpace
            )
            main_fragment_beer_recycler.addItemDecoration(dividerItemDecoration2)

            val dividerItemDecoration = SpacesItemDecorationVerticalDivider(
                context!!,
                topBottomSpace,
                spacingInPixels,
                leftRightSpace
            )
            main_fragment_beer_recycler.addItemDecoration(dividerItemDecoration)
        }
    }

    fun orderList(ascending: Int) {
        beersAdapter.order(ascending)
    }

    fun search(query: String?) {
        // TODO: Limit number of calls to max 1 per second
        viewModel.refreshBeers(query)
        Log.d(TAG, "Searching for: $query")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")

    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")

        super.onDestroyView()
    }


    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        super.onDestroy()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.d(TAG, "onConfigurationChanged")

    }

    override fun onStop() {
        Log.d(TAG, "onStop")

        super.onStop()
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

    }

}
