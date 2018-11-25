package c.scrollview.practiceyoutubeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_recyclerview.layoutManager = LinearLayoutManager(this)
        //main_recyclerview.adapter = MainAdapter()

        fetchJSON()
    }

    fun fetchJSON()
    {
        println("Attempting to fetch")

        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to retrieve data") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()

                val HomeFeed = gson.fromJson(body, HomeFeed::class.java)

                //this fixes the error that a view hierarchy can touch its views
                runOnUiThread {
                    main_recyclerview.adapter = MainAdapter(HomeFeed)
                }
                //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}