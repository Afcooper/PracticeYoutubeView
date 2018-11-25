package c.scrollview.practiceyoutubeview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import c.scrollview.practiceyoutubeview.CustomViewHolder.Companion.VIDEO_TITLE_KEY
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailActivity: AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        main_recyclerview.layoutManager = LinearLayoutManager(this)
        //main_recyclerview.adapter = CourseDetailAdapter()

        val videoID = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)

        val navBarTitle = intent.getStringExtra(VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle

        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoID

        fetchJSON()

    }

    private fun fetchJSON()
    {
        val videoID = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoID

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)
                runOnUiThread {
                    main_recyclerview.adapter = CourseDetailAdapter(courseLessons)
                }
            }

        })
    }


    private class CourseDetailAdapter(val courseLessons: Array<CourseLesson>): RecyclerView.Adapter<CourseViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CourseViewHolder {

            val courseinflater = LayoutInflater.from(parent.context)
            val customView = courseinflater.inflate(R.layout.course_lesson_row, parent, false)
            //val redView = View(parent.context)
            //redView.setBackgroundColor(Color.BLUE)
            //redView.minimumHeight = 50


            return CourseViewHolder(customView)//To change body of created functions use File | Settings | File Templates.

        }

        override fun getItemCount(): Int {
        return courseLessons.size
        }

        override fun onBindViewHolder(p0: CourseViewHolder, p1: Int) {
            val courselessons = courseLessons.get(p1)
            p0.customView.textView_course_lesson_title.text = courselessons.name
            p0.customView.textView2.text = courselessons.duration
            val imageview = p0.customView.imageView
            Picasso.get().load(courselessons.imageUrl).into(imageview)
            p0.courseLesson = courselessons
        }

    }

    class CourseViewHolder(val customView: View, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(customView)
    {
        companion object {
            val COURSE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
        }
        init
        {
            customView.setOnClickListener {
            val intent = Intent(customView.context, CourseLessonActivity::class.java)
                customView.context.startActivity(intent)

                intent.putExtra(COURSE_LESSON_LINK_KEY, courseLesson?.link)
            }
        }
    }
}