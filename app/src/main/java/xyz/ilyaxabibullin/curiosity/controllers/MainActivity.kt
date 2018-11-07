package xyz.ilyaxabibullin.curiosity.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

class MainActivity : MvpAppCompatActivity(), MvpMainView {


    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ListPhotoAdapter

    @InjectPresenter
    lateinit var presenter:MainPresenter

    var photoList = ArrayList<CuriosityPhoto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        presenter.activityWasStarted()

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

    override fun showItem(items: ArrayList<CuriosityPhoto>) {

    }
}
