package com.example.mipt_pd4.database

import android.content.Context
import androidx.room.Room

object DatabaseInitializer {
    private var db: AppDatabase? = null

    fun initializeDatabase(context: Context): AppDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "note"
            ).build()
        }
        return db!!
    }
}
