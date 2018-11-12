package xyz.ilyaxabibullin.curiosity.controllers



import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.utils.OnItemClickListener
import xyz.ilyaxabibullin.curiosity.utils.inflate


class ListPhotoAdapter(var photoList: ArrayList<CuriosityPhoto>):
    RecyclerView.Adapter<ListPhotoAdapter.PhotoHolder>() {



    lateinit var clickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.item)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.date.text = photoList[position].earthDate
        val link = photoList[position].imgSrc
        Glide
            .with(holder.itemView.context)
            .load(link)
            .into(holder.photo)
    }

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        override fun onClick(v: View?) {
            Log.d("ADaptert", "onClick")
            clickListener.onItemClick(adapterPosition,v!!)
        }
        init{
            itemView.setOnClickListener(this)
        }

        val date: TextView = itemView.findViewById(R.id.name_item)
        val photo: ImageView = itemView.findViewById(R.id.item_photo)


    }




}