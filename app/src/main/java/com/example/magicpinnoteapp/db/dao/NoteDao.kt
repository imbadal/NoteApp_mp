package com.example.magicpinnoteapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.magicpinnoteapp.db.entity.NoteModel

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<NoteModel>>
}