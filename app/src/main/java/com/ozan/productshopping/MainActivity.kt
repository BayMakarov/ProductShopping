package com.ozan.productshopping

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.arlib.floatingsearchview.FloatingSearchView
import com.ozan.productshopping.adapter.CategoryAdapter
import com.ozan.productshopping.adapter.EditorSelectedShopsAdapter
import com.ozan.productshopping.adapter.FeatureAdapter
import com.ozan.productshopping.adapter.ProductAdapter
import com.ozan.productshopping.models.*
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    var titlefeaturedOlanlar = ""
    var titleenYeniUrunler = ""
    var titlekategoriler = ""
    var titlekoleksiyonlar = ""
    var titleeditorSecimiVitrin = ""
    var titleenYeniVitrinler = ""

    var featuredOlanlarList : List<featured> = ArrayList()
    var enYeniUrunlerList : List<products> = ArrayList()
    var kategorilerList : List<category> = ArrayList()
    var koleksiyonlarList : List<collections> = ArrayList()
    var editorSecimiVitrinList : List<shop> = ArrayList()
    var enYeniVitrinlerList : List<shop> = ArrayList()

    lateinit var toolbar : Toolbar
    lateinit var floatingSearchView : FloatingSearchView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerViewEnYeni: RecyclerView
    lateinit var recyclerViewKategoriler: RecyclerView
    lateinit var buttonEnYeniler: Button
    lateinit var recyclerViewEditorVitrin: RecyclerView
    lateinit var buttonEditorVitrin: Button
    lateinit var llEditorSelectedShops: LinearLayout
    lateinit var imgEditorSelectedCover: ImageView

    private lateinit var adapter: FeatureAdapter
    private lateinit var productAdapter:  ProductAdapter
    private lateinit var categoryAdapter:  CategoryAdapter
    private lateinit var editorSelectedShopsAdapter:  EditorSelectedShopsAdapter

    private lateinit var viewPager: ViewPager
    var sliderDotspanel: LinearLayout? = null
    private var dotscount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolbar = findViewById(R.id.toolbar)
        floatingSearchView = findViewById(R.id.floating_search_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        viewPager = findViewById(R.id.view_pager)
        sliderDotspanel = findViewById(R.id.slider_dots)
        recyclerViewEnYeni = findViewById(R.id.recyclerView_enyeni)
        buttonEnYeniler = findViewById(R.id.button_en_yeni_tumu)
        recyclerViewKategoriler = findViewById(R.id.recyclerView_kategori)
        buttonEditorVitrin = findViewById(R.id.button_editor_vitrin_tumu)
        recyclerViewEditorVitrin = findViewById(R.id.recyclerView_editor_secimi)
        llEditorSelectedShops = findViewById(R.id.ll_editor_secimi_vitrin)
        imgEditorSelectedCover = findViewById(R.id.img_editor_selected)


        callService()

        floatingSearchView.setOnMenuItemClickListener { item ->
            if (item != null) {
                val id = item.itemId

                if (id == R.id.action_voice_rec) {

                    val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    // Language model defines the purpose, there are special models for other use cases, like search.

                    // Text that shows up on the Speech input prompt.
                    sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
                    try {
                        // Start the intent for a result, and pass in our request code.
                        startActivityForResult(sttIntent, 1)
                    } catch (e: ActivityNotFoundException) {
                        // Handling error when the service is not available.
                        e.printStackTrace()

                    }

                }

            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            callService()
            swipeRefreshLayout.isRefreshing = false
        }

        buttonEnYeniler.setOnClickListener {

            Genericfiles.enYeniUrunlerGenericList = enYeniUrunlerList

            val intent = Intent(this@MainActivity, EnYeniUrunlerActivity::class.java)
            startActivity(intent)
            finish()

        }

        buttonEditorVitrin.setOnClickListener {

            Genericfiles.editorSelectedShopsGenericList = editorSecimiVitrinList

            val intent = Intent(this@MainActivity, EditorSelectedShopsActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun callService() {


        val destinationService  = ServiceBuilder.buildService(Service::class.java)
        val requestCall =destinationService.getDiscover()

        requestCall.enqueue(object : Callback<List<DownloadModel>>{

            override fun onFailure(call: Call<List<DownloadModel>>, t: Throwable) {

                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }


            override fun onResponse(
                call: Call<List<DownloadModel>>,
                response: Response<List<DownloadModel>>
            ) {

                if (response.isSuccessful && response.body() != null && response.body()!!.size == 6){

                    featuredOlanlarList = response.body()!![0].featured
                    enYeniUrunlerList = response.body()!![1].products
                    kategorilerList = response.body()!![2].categories
                    koleksiyonlarList = response.body()!![3].collections
                    editorSecimiVitrinList = response.body()!![4].shops
                    enYeniVitrinlerList = response.body()!![5].shops

                    titlefeaturedOlanlar = response.body()!![0].title
                    titleenYeniUrunler = response.body()!![1].title
                    titlekategoriler = response.body()!![2].title
                    titlekoleksiyonlar = response.body()!![3].title
                    titleeditorSecimiVitrin = response.body()!![4].title
                    titleenYeniVitrinler = response.body()!![5].title

                    setupUi()

                }
                else{
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun setupUi() {

        //Öne Çıkanlar
        adapter = FeatureAdapter(featuredOlanlarList, this@MainActivity)
        viewPager.adapter = adapter

        var dotscount = adapter.count
        val dots = arrayOfNulls<ImageView>(dotscount)

        viewPager.setOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                toggleRefreshing(state == ViewPager.SCROLL_STATE_IDLE)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int){}

            override fun onPageSelected(position: Int) {

                for (i in 0 until dotscount) {
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MainActivity,R.drawable.non_active_dot)
                    )
                }
                dots[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,R.drawable.active_dot)
                )

            }
        })

        sliderDotspanel!!.removeAllViews()

        for (i in 0 until dotscount) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(this,
                    R.drawable.non_active_dot))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            sliderDotspanel!!.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                this, R.drawable.active_dot)
        )


        //Ürünler
        productAdapter = ProductAdapter(enYeniUrunlerList)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewEnYeni.layoutManager = mLayoutManager
        recyclerViewEnYeni.itemAnimator = DefaultItemAnimator()
        recyclerViewEnYeni.adapter = productAdapter

        recyclerViewEnYeni.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                toggleRefreshing(newState == RecyclerView.SCROLL_STATE_IDLE)
            }
        })

        //KATEGORİLER

        categoryAdapter = CategoryAdapter(kategorilerList)
        val mLayoutManager2 = LinearLayoutManager(applicationContext)
        mLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewKategoriler.layoutManager = mLayoutManager2
        recyclerViewKategoriler.itemAnimator = DefaultItemAnimator()
        recyclerViewKategoriler.adapter = categoryAdapter

        recyclerViewKategoriler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                toggleRefreshing(newState == RecyclerView.SCROLL_STATE_IDLE)
            }
        })

        //Editor Selected Shops

        editorSelectedShopsAdapter = EditorSelectedShopsAdapter(editorSecimiVitrinList)
        val mLayoutManager3 = LinearLayoutManager(applicationContext)
        mLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewEditorVitrin.layoutManager = mLayoutManager3
        recyclerViewEditorVitrin.itemAnimator = DefaultItemAnimator()
        recyclerViewEditorVitrin.adapter = editorSelectedShopsAdapter

        if (editorSecimiVitrinList.isNotEmpty()) {
            Picasso.get().load(editorSecimiVitrinList[0].cover?.url).fit().centerCrop().into(imgEditorSelectedCover)


        }


        recyclerViewEditorVitrin.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                toggleRefreshing(newState == RecyclerView.SCROLL_STATE_IDLE)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = mLayoutManager3.childCount
                val totalItemCount: Int = mLayoutManager3.itemCount
                val firstVisibleItemPosition: Int = mLayoutManager3.findFirstVisibleItemPosition()
                val lastItem = firstVisibleItemPosition + visibleItemCount

                try {
                    Picasso.get().load(editorSecimiVitrinList[firstVisibleItemPosition].cover?.url)
                        .fit().centerCrop().into(imgEditorSelectedCover)
                }
                catch (e : ArrayIndexOutOfBoundsException){

                }

            }
        })

    }

    fun toggleRefreshing(enabled: Boolean) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.isEnabled = enabled
        }
    }
    

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            1 -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        floatingSearchView.setSearchText(recognizedText)
                    }
                }
            }
        }
    }
}


//Interface to call service
interface Service {

    @GET("discover")
    fun getDiscover () : Call<List<DownloadModel>>

}

//REtrofit Builder
object ServiceBuilder {
    private const val URL ="https://www.vitrinova.com/api/v2/"
    //CREATE HTTP CLIENT
    private val okHttp = OkHttpClient.Builder()

    //retrofit builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //create retrofit Instance
    private val retrofit = builder.build()

    //we will use this class to create an anonymous inner class function that
    //implements Country service Interface


    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }

}