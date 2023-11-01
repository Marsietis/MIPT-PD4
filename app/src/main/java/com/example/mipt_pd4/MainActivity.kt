package com.example.mipt_pd4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mipt_pd4.database.AppDatabase
import com.example.mipt_pd4.database.DatabaseInitializer
import com.example.mipt_pd4.database.Note
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var notesList: ListView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate")

        setSupportActionBar(findViewById(R.id.materialToolbar))

        notesList = findViewById(R.id.notesList)

        db = DatabaseInitializer.initializeDatabase(this)
    }

    // Use Kotlin coroutines to perform the database query on a background thread
    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")

        GlobalScope.launch(Dispatchers.IO) {
            val noteDao = db.noteDao()
            val notes: List<Note> = noteDao.getAll()

            withContext(Dispatchers.Main) {
                val adapter = NoteAdapter(this@MainActivity, notes)
                notesList.adapter = adapter
                Log.d("MainActivity", "notesList.adapter set")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d("MainActivity", "onCreateOptionsMenu")
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.notes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "onOptionsItemSelected")
        // Handle options item selection.
        return when (item.itemId) {
            R.id.add_note -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.delete_note -> {
                val intent = Intent(this, DeleteNoteActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}