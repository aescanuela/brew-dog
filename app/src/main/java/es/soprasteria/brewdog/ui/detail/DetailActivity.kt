package es.soprasteria.brewdog.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.soprasteria.brewdog.R
import es.soprasteria.brewdog.constants.AppConstants
import es.soprasteria.brewdog.model.Beer
import es.soprasteria.brewdog.networking.DownloadImageTask
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {


    private var mBeer: Beer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra(AppConstants.INTENT_EXTRA_BEER)) {
            mBeer = intent.getParcelableExtra(AppConstants.INTENT_EXTRA_BEER)
        } else {
            Toast.makeText(
                this, getString(
                    R.string.error_getting_beer
                ), Toast.LENGTH_LONG
            ).show()
            finish()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (mBeer != null) {
            detail_activity_name_text.text = mBeer!!.name
            detail_activity_tagline_text.text = mBeer!!.tagline
            detail_activity_description_text.text = mBeer!!.description
            detail_activity_avb_text.text = getString(R.string.abv_percentage, mBeer?.abv ?: 0.0)

            if (mBeer?.imageUrl != null) {
                DownloadImageTask(
                    detail_activity_image,
                    mBeer!!.imageUrl!!
                ).execute()
            }
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.home -> {
//                onBackPressed()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
