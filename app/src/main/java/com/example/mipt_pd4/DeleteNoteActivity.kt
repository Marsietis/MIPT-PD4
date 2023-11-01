package com.example.mipt_pd4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.mipt_pd4.database.AppDatabase
import com.example.mipt_pd4.database.DatabaseInitializer
import com.example.mipt_pd4.database.Note
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var notesSpinner: Spinner
    private lateinit var noteNamesAdapter: ArrayAdapter<String> // ArrayAdapter for note names

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_note)

        setSupportActionBar(findViewById(R.id.materialToolbar))

        notesSpinner = findViewById(R.id.notesSpinner)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        db = DatabaseInitializer.initializeDatabase(this)

        // Create the ArrayAdapter for note names
        noteNamesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ArrayList())
        noteNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        notesSpinner.adapter = noteNamesAdapter

        deleteButton.setOnClickListener {
            val noteName = notesSpinner.selectedItem.toString()

            GlobalScope.launch(Dispatchers.IO) {
                val noteDao = db.noteDao()

                // Get the note with the selected name
                val noteToDelete = noteDao.getNoteByName(noteName)

                if (noteToDelete != null) {
                    // Delete the note
                    noteDao.delete(noteToDelete)
                }
            }

            finish()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.IO) {
            val noteDao = db.noteDao()
            val notes: List<Note> = noteDao.getAll()

            val noteNames = notes.map { it.name }
            runOnUiThread {
                noteNamesAdapter.clear()
                noteNamesAdapter.addAll(noteNames)
                noteNamesAdapter.notifyDataSetChanged()
            }
        }
    }
}