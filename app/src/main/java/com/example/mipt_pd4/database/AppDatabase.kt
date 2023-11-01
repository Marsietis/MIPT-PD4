package com.example.mipt_pd4.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mipt_pd4.database.Note
import com.example.mipt_pd4.database.NoteDao

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
