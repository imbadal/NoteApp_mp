package com.example.magicpinnoteapp.repository

import androidx.lifecycle.LiveData
import com.example.magicpinnoteapp.db.dao.NoteDao
import com.example.magicpinnoteapp.db.entity.NoteModel

class NoteRepository(val dao: NoteDao) {

    suspend fun insertNote(note: NoteModel) = dao.insertNote(note)

    suspend fun updateNote(note: NoteModel) = dao.updateNote(note)

    suspend fun deleteNote(note: NoteModel) = dao.deleteNote(note)

    fun getAllNotes(): LiveData<List<NoteModel>> = dao.getAllNotes()

}