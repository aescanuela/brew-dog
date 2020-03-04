package es.soprasteria.brewdog.networking

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView

/**
 * Async Task to download image
 */
class DownloadImageTask(
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
