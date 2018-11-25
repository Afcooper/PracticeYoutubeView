package c.scrollview.practiceyoutubeview

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homefeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    val TitleNames = arrayListOf<String>("First Title", "Second Title", "Third Title", "4", "5", "6")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, p0, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return homefeed.videos.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        val video = homefeed.videos.get(position)
        holder.view.textView_video_title.text = video.name
        holder.view.textView_Channel_Name.text = video.channel.name

        val thumbnail = holder.view.imageView_video_thumbnail
        Picasso.get().load(video.imageUrl).into(thumbnail)

        val channelpicture = holder.view.imageView_channel_profile
        Picasso.get().load(video.channel.profileImageUrl).into(channelpicture)

        holder?.video = video

    }

}


class CustomViewHolder(val view: View, var video: Video? = null): RecyclerView.ViewHolder(view){
    companion object {
        val VIDEO_TITLE_KEY = "Video_Title"
        val VIDEO_ID_KEY = "Video_Id"
    }

    init {
        view.setOnClickListener {

            val intent = Intent(view.context, CourseDetailActivity::class.java)

            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)

            view.context.startActivity(intent)
        }
    }
}