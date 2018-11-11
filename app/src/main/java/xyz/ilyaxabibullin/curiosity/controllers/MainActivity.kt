package xyz.ilyaxabibullin.curiosity.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import xyz.ilyaxabibullin.curiosity.PaginationScrollListener
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

class MainActivity : MvpAppCompatActivity(), MvpMainView {

    val TAG = MainActivity::class.java.name

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListPhotoAdapter

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

        adapter = ListPhotoAdapter(photoList)
        recyclerView.adapter = this.adapter
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
        }
        )

    }


    override fun showItem(items: ArrayList<CuriosityPhoto>) {
        isLoading = false

        photoList.addAll(items)
        Log.d(TAG,"колличество итемов загруженных = ${items.size}")
        Log.d(TAG,"колличество итемов всего = ${photoList.size}")

        recyclerView.adapter = ListPhotoAdapter(photoList)
        recyclerView.scrollToPosition(lastPosition)

    }


}
