package es.soprasteria.brewdog.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import es.soprasteria.brewdog.R
import es.soprasteria.brewdog.constants.AppConstants


/**
 * First Activity that show the list of beers
 */
class MainActivity : AppCompatActivity() {


    // Instance of the fragment shown
    var fragmentInstance: MainFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the first fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        // Handle search intent
        handleIntent(intent)
    }


    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        // Save the fragment attached
        if (fragment is MainFragment) {
            fragmentInstance = fragment
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.app_bar_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            // Accept also empty queries (so we can show entire list again after user deletes query String)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.equals("")) {
                        fragmentInstance?.search(query)
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.equals("")) {
                        this.onQueryTextSubmit("")
                        return true
                    }
                    return false
                }
            })
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                true
            }
            R.id.app_bar_lighter_first -> {
                fragmentInstance?.orderList(AppConstants.Direction.ASCENDING)
                true
            }
            R.id.app_bar_stronger_first -> {
                fragmentInstance?.orderList(AppConstants.Direction.DESCENDING)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }


    private fun handleIntent(intent: Intent) {
        // Handle search Intent
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            fragmentInstance?.search(query)
        }
    }


}
