package com.example.mipt_pd4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.example.mipt_pd4.database.AppDatabase
import com.example.mipt_pd4.database.Note
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val noteName = findViewById<EditText>(R.id.noteName)
        val noteContent = findViewById<EditText>(R.id.noteContent)
        val saveNoteBtn = findViewById<Button>(R.id.saveNote)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "note"
        ).build()

        val noteDao = db.noteDao()

        saveNoteBtn.setOnClickListener {
            val name = noteName.text.toString()
            val content = noteContent.text.toString()

            if (name.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(
                    id = 0, // Let Room generate the id
                    name = name,
                    content = content
                )
                // Insert the note in a background thread
                GlobalScope.launch(Dispatchers.IO) {
                    noteDao.insertAll(note)
                }

                finish()
            }
        }
    }
}