package xyz.ilyaxabibullin.curiosity.controllers


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.utils.OnItemClickListener
import xyz.ilyaxabibullin.curiosity.utils.inflate


class ListPhotoAdapter(var photoList: ArrayList<CuriosityPhoto>) :


    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false

    lateinit var clickListener: OnItemClickListener


    companion object {
        private const val ITEM = 0
        private const val LOAD = 1

    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        lateinit var holder:RecyclerView.ViewHolder
        lateinit var inflatedView: View
        println("position $position")
        when (position) {
            ITEM -> {
                inflatedView = parent.inflate(R.layout.item)
                holder = PhotoHolder(inflatedView)
            }
            LOAD -> {
                inflatedView = parent.inflate(R.layout.item_progress)
                holder = LoadHolder(inflatedView)
            }
        }

        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == photoList.size - 1 && isLoading) LOAD else ITEM
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (getItemViewType(position)) {
            ITEM -> {
                val photoVH = holder as (PhotoHolder)

                holder.date.text = photoList[position].earthDate
                val link = photoList[position].imgSrc
                holder.id.text = photoList[position].id.toString()
                Glide
                    .with(holder.itemView.context)
                    .load(link)
                    .into(holder.photo)
            }
            LOAD -> {
                //nothing
            }
        }

    }

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            Log.d("Adapter", "onClick")
            clickListener.onItemClick(adapterPosition, v!!)
        }

        init {
            itemView.setOnClickListener(this)
        }

        val date: TextView = itemView.findViewById(R.id.name_item)
        val photo: ImageView = itemView.findViewById(R.id.item_photo)
        val id: TextView = itemView.findViewById(R.id.id_photo_view)

    }

    inner class LoadHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addLoadingFooter() {
        isLoading = true
        add(CuriosityPhoto())
    }

    fun removeLoadingFooter() {
        isLoading = false
        val pos = photoList.size - 1
        val item = photoList[pos]

        photoList.removeAt(pos)
        notifyItemRemoved(pos)

    }

    fun add(i: CuriosityPhoto){
        photoList.add(i)
        notifyItemInserted(photoList.size - 1)
        photoList.sortByDescending { selector(it) }
    }

    fun addAll(items: ArrayList<CuriosityPhoto>) {
        println("addAll()")
        for(i in items){
            add(i)
        }
    }

    private fun selector(p: CuriosityPhoto): Int = p.id

}