package com.example.magicpinnoteapp.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magicpinnoteapp.databinding.LayoutNotesRowBinding

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = LayoutNotesRowBinding.bind(itemView)
    val tvTitle: TextView = binding.tvNoteTitle
    val tvDetails: TextView = binding.tvNoteDetails
    val ivIcon: ImageView = binding.ivIcon
    val ivSettings: ImageView = binding.ivSettings
}