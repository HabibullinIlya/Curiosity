package xyz.ilyaxabibullin.curiosity.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ListPhotoAdapter

    var photoList = ArrayList<CuriosityPhoto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

    }

    private fun initViews(){
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fakeData()
        adapter = ListPhotoAdapter(photoList)
        recyclerView.adapter = this.adapter

    }

    private fun fakeData(){

        photoList.add(CuriosityPhoto("07:11:18","kek"))
        photoList.add(CuriosityPhoto("07:11:18","kek"))
        photoList.add(CuriosityPhoto("07:11:18","kek"))
        photoList.add(CuriosityPhoto("07:11:18","kek"))
        photoList.add(CuriosityPhoto("07:11:18","kek"))
        photoList.add(CuriosityPhoto("07:11:18","kek"))
        photoList.add(CuriosityPhoto("07:11:18","kek"))
    }
}
