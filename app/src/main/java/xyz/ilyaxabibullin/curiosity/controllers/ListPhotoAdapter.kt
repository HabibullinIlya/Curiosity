package xyz.ilyaxabibullin.curiosity.controllers


import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.inflate


class ListPhotoAdapter(var photoList: ArrayList<CuriosityPhoto>) :
    RecyclerView.Adapter<ListPhotoAdapter.PhotoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.item)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.date.text = photoList[position].earthDate

    }

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.name_item)
        val photo: ImageView = itemView.findViewById(R.id.item_photo)

    }
}