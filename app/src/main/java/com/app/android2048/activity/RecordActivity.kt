package com.app.android2048.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.android2048.R
import com.app.android2048.util.JSONHelper.importFromJSON
import com.app.android2048.view.RecordsAdapter
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val recordsList = findViewById<RecyclerView>(R.id.records_list)
        val listRecords = importFromJSON(this)
        if (listRecords != null){
            val itemsSorted = listRecords.sortedWith(compareBy({-it.score}, {it.name.toUpperCase()}))
            val recordsAdapter = RecordsAdapter(itemsSorted)
            recordsList.adapter = recordsAdapter
        } else {
            recordsList.visibility = View.GONE
            no_record.visibility = View.VISIBLE
        }
    }

    fun buttonLink (view: View) {
        startActivity(Intent(this@RecordActivity, MainActivity::class.java))
    }

}
