package com.example.mipt_pd4

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room.databaseBuilder
import com.example.mipt_pd4.database.AppDatabase
import com.example.mipt_pd4.database.Note


class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.materialToolbar))

        // Initialize the database in the onCreate method
        db = databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "notes"
        ).build()

        val notesList = findViewById<ListView>(R.id.notesList)
        val noteDao = db.noteDao()

        val notes: List<Note> = noteDao.getAll()

        val adapter = ArrayAdapter(this, R.layout.list_item, notes)
        notesList.adapter = adapter
        notesList.setOnItemClickListener { _, _, position, _ ->
            val selectedNote = notes[position]
            // Handle the selected note, e.g., show a dialog with the name and content
            val name = selectedNote.name
            val content = selectedNote.content
            // Display or process the name and content as needed
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.notes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
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