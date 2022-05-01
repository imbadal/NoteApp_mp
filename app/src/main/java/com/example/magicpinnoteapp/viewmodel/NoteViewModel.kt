package com.example.magicpinnoteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.magicpinnoteapp.db.AppDatabase
import com.example.magicpinnoteapp.db.entity.NoteModel
import com.example.magicpinnoteapp.repository.NoteRepository
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    private val allNotes: LiveData<List<NoteModel>>

    init {
        val dao = AppDatabase.getInstance(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.getAllNotes()
    }

    fun insertNote(note: NoteModel) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun updateNote(note: NoteModel) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }


    fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun getAllNotes(): LiveData<List<NoteModel>> {
        return allNotes
    }

    fun isValidInputs(noteTitle: String): Boolean {
        if (noteTitle.isEmpty()) {
            return false
        }
        return true
    }
}