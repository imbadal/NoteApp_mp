package com.example.magicpinnoteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.magicpinnoteapp.db.dao.NoteDao
import com.example.magicpinnoteapp.db.entity.NoteModel

@Database(entities = [NoteModel::class], exportSchema = false, version = 1)
@TypeConverters(Convertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let { return it }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}