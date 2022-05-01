package com.example.magicpinnoteapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.magicpinnoteapp.R
import com.example.magicpinnoteapp.databinding.FragmentUpdateNoteBinding
import com.example.magicpinnoteapp.db.entity.NoteModel
import com.example.magicpinnoteapp.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment() {

    var selectedNote: NoteModel? = null
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var binding: FragmentUpdateNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        getArgs(arguments)
        initUI()
        setOnClickListener()
        savedInstanceState?.clear()
        return binding.root
    }

    private fun getArgs(arguments: Bundle?) {
        selectedNote = arguments?.getParcelable("selectedNote")
        arguments?.clear()
    }

    private fun setOnClickListener() {
        binding.btnUpdate.setOnClickListener {
            if (viewModel.isValidInputs(binding.etNoteTitle.text.toString())) {
                updateNote()
                findNavController().navigate(R.id.action_updateNoteFragment_to_noteListFragment)
            } else {
                Toast.makeText(context, "Please enter Note Title", Toast.LENGTH_LONG).show()
            }
        }

        binding.ivNoteIcon.setOnClickListener { openImagePicker() }
        binding.ivEditIcon.setOnClickListener { openImagePicker() }
    }

    private fun getNote(): NoteModel {
        val noteID = selectedNote?.id ?: 0
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteDetails = binding.etNoteDetails.text.toString()
        val imageBitmap = binding.ivNoteIcon.drawable.toBitmap()
        return NoteModel(noteID, noteTitle, noteDetails, imageBitmap)
    }

    private fun updateNote() {
        val note = getNote()
        viewModel.updateNote(note)
    }

    private fun initUI() {
        binding.etNoteTitle.setText(selectedNote?.noteTitle)
        binding.etNoteDetails.setText(selectedNote?.noteDetails)
        binding.ivNoteIcon.setImageBitmap(selectedNote?.icon)
    }


    private fun openImagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        _galleryLauncher.launch(intent)
    }

    private val _galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    binding.ivNoteIcon.setImageURI(selectedImageUri)
                }
            }
        }
}