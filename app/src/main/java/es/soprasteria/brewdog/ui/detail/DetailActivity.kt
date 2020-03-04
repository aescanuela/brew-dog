package es.soprasteria.brewdog.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.soprasteria.brewdog.R
import es.soprasteria.brewdog.constants.AppConstants
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.networking.DownloadImageTask
import kotlinx.android.synthetic.main.activity_detail.*


/**
 * Activity to show the detail for a Beer
 */
class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Show back button in AppBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Beer to show
        var beer: Beer? = null
        if (intent.hasExtra(AppConstants.INTENT_EXTRA_BEER)) {

            // Get beer from Intent
            beer = intent.getParcelableExtra(AppConstants.INTENT_EXTRA_BEER)
        } else {
            Toast.makeText(
                this, getString(
                    R.string.error_getting_beer
                ), Toast.LENGTH_LONG
            ).show()
            finish()
        }

        if (beer != null) {

            detail_activity_name_text.text = beer.name
            detail_activity_tagline_text.text = beer.tagline
            detail_activity_description_text.text = beer.description
            detail_activity_avb_text.text = getString(R.string.abv_percentage, beer.abv ?: 0.0)

            // Download image in an AsyncTask
            if (beer.imageUrl != null) {
                DownloadImageTask(
                    detail_activity_image,
                    beer.imageUrl!!
                ).execute()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
