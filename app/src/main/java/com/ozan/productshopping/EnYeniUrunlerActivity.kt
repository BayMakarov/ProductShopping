package com.ozan.productshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozan.productshopping.adapter.ProductAdapter

class EnYeniUrunlerActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter:  ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_en_yeni_urunler)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar!!.title = "En Yeni Ürünler"

        }

        recyclerView = findViewById(R.id.recyclerView_enyeni)

        productAdapter = ProductAdapter(Genericfiles.enYeniUrunlerGenericList)
        val manager = GridLayoutManager(this@EnYeniUrunlerActivity, 2)
        manager.orientation = GridLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = productAdapter

    }


    override fun onBackPressed() {

        val intent = Intent(this@EnYeniUrunlerActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == android.R.id.home){
            val intent = Intent(this@EnYeniUrunlerActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return true
    }
}