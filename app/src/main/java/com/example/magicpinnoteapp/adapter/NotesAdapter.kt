package com.example.magicpinnoteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magicpinnoteapp.databinding.LayoutNotesRowBinding
import com.example.magicpinnoteapp.db.entity.NoteModel
import com.example.magicpinnoteapp.viewHolder.NotesViewHolder

class NotesAdapter : RecyclerView.Adapter<NotesViewHolder>() {

    var noteList: List<NoteModel> = mutableListOf()
    private var clickListener: NoteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            LayoutNotesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = noteList[position]
        with(holder) {
            tvTitle.text = note.noteTitle
            tvDetails.text = note.noteDetails
            ivIcon.setImageBitmap(note.icon)
        }
        holder.ivSettings.setOnClickListener { clickListener?.onMoreClick(note) }
        holder.itemView.setOnClickListener { clickListener?.onItemClick(note) }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(noteList: List<NoteModel>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    fun setNoteClickListener(clickListener: NoteClickListener) {
        this.clickListener = clickListener
    }
}

interface NoteClickListener {
    fun onMoreClick(note: NoteModel)
    fun onItemClick(note: NoteModel)
}