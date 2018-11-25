package c.scrollview.practiceyoutubeview

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course_lesson)
        //WebView_Course_Lesson.setBackgroundColor(Color.YELLOW)
        val courselink = intent.getStringExtra(CourseDetailActivity.CourseViewHolder.COURSE_LESSON_LINK_KEY)
        WebView_Course_Lesson.settings.javaScriptEnabled = true
        WebView_Course_Lesson.settings.loadWithOverviewMode = true
        WebView_Course_Lesson.settings.useWideViewPort = true

        WebView_Course_Lesson.loadUrl(courselink)
    }
}