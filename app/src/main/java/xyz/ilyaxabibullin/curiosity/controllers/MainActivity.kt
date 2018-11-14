package xyz.ilyaxabibullin.curiosity.controllers

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.utils.OnItemClickListener

class MainActivity : MvpAppCompatActivity(), MvpMainView {


    val TAG = MainActivity::class.java.name

    private lateinit var recyclerView: RecyclerView
    private lateinit var listPhotoAdapter: ListPhotoAdapter

    var manager = LinearLayoutManager(this)
    var lastPage = false
    var isLoading = false
    var lastPosition = 0//костыль

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private var photoList = ArrayList<CuriosityPhoto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        presenter.activityWasStarted()



    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = manager

        listPhotoAdapter = ListPhotoAdapter(photoList)


        recyclerView.adapter = this.listPhotoAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var visibleItemCount = manager.childCount
                var totalItemCount = manager.itemCount
                var firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                Log.d(
                    TAG, "visibleCount: $visibleItemCount totalItemCount: $totalItemCount  " +
                            "firstVisibleItem: $firstVisibleItemPosition"
                )
                Log.d(TAG, "is loading $isLoading  lastPage $lastPage")
                if (!isLoading && !lastPage) {
                    Log.d(TAG, "in first if")
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        isLoading = true
                        Log.d(TAG, "in second if")
                        lastPosition = firstVisibleItemPosition
                        presenter.activityWasScrolled()
                    }

                }

            }
        })
        listPhotoAdapter.clickListener = object:OnItemClickListener{
            override fun onItemClick(position: Int, view: View) {
                Toast.makeText(this@MainActivity,"$position",Toast.LENGTH_SHORT).show()
            }

        }



    }


    override fun showItem(items: ArrayList<CuriosityPhoto>) {
        isLoading = false

        photoList.addAll(items)
        Log.d(TAG, "колличество итемов загруженных = ${items.size}")
        Log.d(TAG, "колличество итемов всего = ${photoList.size}")
        listPhotoAdapter = ListPhotoAdapter(photoList)
        listPhotoAdapter.clickListener = object: OnItemClickListener{
            override fun onItemClick(position: Int, view: View) {
                presenter.itemWasClicked(position)
            }

        }
        photoList.sortByDescending {selector(it)}

        recyclerView.adapter = listPhotoAdapter
        recyclerView.scrollToPosition(lastPosition)


    }
    fun selector(p :CuriosityPhoto):Int = p.id


    override fun navigateToDetailView(cls: Class<*>,position:Int) {
        val intent = Intent(this,cls)
        intent.putParcelableArrayListExtra("photos", photoList as java.util.ArrayList<out Parcelable>)
        intent.putExtra("position", position)
        startActivity(intent)
    }


}
