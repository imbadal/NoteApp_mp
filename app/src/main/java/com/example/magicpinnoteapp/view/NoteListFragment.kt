package com.example.magicpinnoteapp.view

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magicpinnoteapp.view.dialog.NoteBottomSheetDialog
import com.example.magicpinnoteapp.R
import com.example.magicpinnoteapp.adapter.NoteClickListener
import com.example.magicpinnoteapp.adapter.NotesAdapter
import com.example.magicpinnoteapp.databinding.BottomSheetLayoutBinding
import com.example.magicpinnoteapp.databinding.FragmentNoteListBinding
import com.example.magicpinnoteapp.db.entity.NoteModel
import com.example.magicpinnoteapp.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.ByteArrayOutputStream


class NoteListFragment : Fragment(), NoteClickListener {
    lateinit var binding: FragmentNoteListBinding
    lateinit var noteAdapter: NotesAdapter
    lateinit var mContext: Context
    private val viewModel: NoteViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        initRecyclerView()
        initObservers()
        initClickListener()
        return binding.root
    }

    private fun initObservers() {
        viewModel.getAllNotes().observe(viewLifecycleOwner) {
            noteAdapter.setData(it)
        }
    }

    private fun initRecyclerView() {
        noteAdapter = NotesAdapter()
        noteAdapter.setNoteClickListener(this)
        binding.rvNotes.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = noteAdapter
        }
    }

    private fun initClickListener() {
        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }
    }

    override fun onMoreClick(note: NoteModel) {
        showBtnSheet(note)
    }

    override fun onItemClick(note: NoteModel) {
        displayNote(note)
    }

    private fun showBtnSheet(note: NoteModel) {
        val bottomSheetBinding = BottomSheetLayoutBinding.inflate(layoutInflater, null, false)
        val bottomSheetDialog = BottomSheetDialog(mContext)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
        onBottomSheetClickHandler(bottomSheetDialog, bottomSheetBinding, note)
    }

    private fun onBottomSheetClickHandler(
        bottomSheetDialog: BottomSheetDialog,
        bottomSheetBinding: BottomSheetLayoutBinding,
        note: NoteModel
    ) {
        bottomSheetBinding.tvEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("selectedNote", note)
            findNavController().navigate(R.id.action_noteListFragment_to_updateNoteFragment, bundle)
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.tvView.setOnClickListener {
            displayNote(note)
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.tvDelete.setOnClickListener {
            viewModel.deleteNote(note)
            Toast.makeText(activity, "Deleted successfully", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }
    }

    private fun displayNote(note: NoteModel) {
        val bottomSheetDialog = NoteBottomSheetDialog()
        val bundle = Bundle()
        bundle.putString("title", note.noteTitle)
        bundle.putString("details", note.noteDetails)

        val outputStream = ByteArrayOutputStream()
        note.icon.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        bundle.putByteArray("icon", outputStream.toByteArray())
        bottomSheetDialog.arguments = bundle

        bottomSheetDialog.show(
            (context as AppCompatActivity).supportFragmentManager,
            "bottomSheetDialog"
        )
    }
}