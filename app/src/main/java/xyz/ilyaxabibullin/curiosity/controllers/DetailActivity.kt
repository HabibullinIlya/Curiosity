package xyz.ilyaxabibullin.curiosity.controllers


import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle

import android.util.Log
import android.view.*
import android.widget.ImageView

import com.arellomobile.mvp.MvpAppCompatActivity
import com.bumptech.glide.Glide

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition
import com.github.chrisbanes.photoview.PhotoView

import kotlinx.android.synthetic.main.activity_detail.*
import xyz.ilyaxabibullin.curiosity.R
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

class DetailActivity : MvpAppCompatActivity() {

    val TAG = DetailActivity::class.java.name

    lateinit var photoList: ArrayList<CuriosityPhoto>
    var position = -1


    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        photoList = intent.getParcelableArrayListExtra("photos")
        position = intent.getIntExtra("position",-1)

        Log.d(TAG,"${photoList[0].earthDate}  date of 0 photos")
        Log.d(TAG,"$position position")


        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,photoList)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.currentItem = position

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }



    inner class SectionsPagerAdapter(fm: FragmentManager, photoList: ArrayList<CuriosityPhoto>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(
                position,photoList[position].earthDate,photoList[position].imgSrc
            )
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return photoList.size
        }
    }


    class PlaceholderFragment : Fragment() {

        var name = ""
        var url = ""
        var position = 0


        private lateinit var imageView: ImageView

        override fun setArguments(args: Bundle?) {
            super.setArguments(args)
            this.position = args!!.getInt(ARG_SECTION_NUMBER)
            this.name = args.getString(ARG_IMG_TITLE)!!
            this.url = args.getString(ARG_IMG_URL)!!
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_detail, container, false)


            imageView = rootView.findViewById(R.id.detail_image) as PhotoView
            Glide
                .with(activity!!)
                .asBitmap()
                .load(url)
                .into(object:SimpleTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        startPostponedEnterTransition();
                        imageView.setImageBitmap(resource);
                    }

                })
            return rootView
        }

        companion object {

            private const val ARG_SECTION_NUMBER = "section_number"
            private const val ARG_IMG_TITLE = "image_title"
            private const val ARG_IMG_URL = "image_url"

            fun newInstance(sectionNumber: Int, name: String, url: String): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                args.putString(ARG_IMG_TITLE, name)
                args.putString(ARG_IMG_URL, url)

                fragment.arguments = args
                return fragment
            }
        }
    }
}
