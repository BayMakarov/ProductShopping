package com.ozan.productshopping

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozan.productshopping.adapter.EditorSelectedShopsAdapter


class EditorSelectedShopsActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private lateinit var editorSelectedShopsAdapter: EditorSelectedShopsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_selected_shops)

        recyclerView = findViewById(R.id.recyclerView_editor_select)


        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar!!.title = "Editör Seçimi Vitrinler"

        }

        editorSelectedShopsAdapter = EditorSelectedShopsAdapter(Genericfiles.editorSelectedShopsGenericList)
        val manager = GridLayoutManager(this@EditorSelectedShopsActivity, 1)
        manager.orientation = GridLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = editorSelectedShopsAdapter

    }

    override fun onBackPressed() {

        val intent = Intent(this@EditorSelectedShopsActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == android.R.id.home){
            val intent = Intent(this@EditorSelectedShopsActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return true
    }
}