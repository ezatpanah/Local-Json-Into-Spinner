package com.ezatpanah.localjsonintospinner

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.ezatpanah.localjsonintospinner.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var countriesListAdapter: ArrayAdapter<String>
    private var countriesList: MutableList<String> = ArrayList()
    private var valueList: MutableList<String> = ArrayList()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val json = loadJsonObjectFromAsset("countries.json")
            try {
                val refArray = json!!.getJSONArray("countries")
                for (i in 0 until refArray.length()) {
                    val country = refArray.getJSONObject(i).getString("name")
                    val value = refArray.getJSONObject(i).getString("value")
                    countriesList.add(country)
                    valueList.add(value)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            countriesListAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, countriesList)
            spnrCountry.setAdapter(countriesListAdapter)

            spnrCountry.setOnItemClickListener { _, _, i, _ ->
                tvCountryName.text = countriesList[i]
                tvCountryCode.text = valueList[i]
            }

        }


    }


    private fun loadJsonObjectFromAsset(assetName: String): JSONObject? {
        try {
            val json = loadStringFromAsset(assetName)
            if (json != null) return JSONObject(json)
        } catch (e: Exception) {
            Log.e("JsonUtils", e.toString())
        }
        return null
    }

    @Throws(Exception::class)
    private fun loadStringFromAsset(assetName: String): String? {
        val `is`: InputStream = this.assets.open(assetName)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer)
    }


}