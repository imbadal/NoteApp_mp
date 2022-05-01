package com.example.magicpinnoteapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.magicpinnoteapp.viewmodel.NoteViewModel
import com.example.magicpinnoteapp.R
import com.example.magicpinnoteapp.databinding.FragmentAddNoteBinding
import com.example.magicpinnoteapp.db.entity.NoteModel


class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private val viewModel: NoteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSave.setOnClickListener {
            if (viewModel.isValidInputs(binding.etNoteTitle.text.toString())) {
                saveNote()
                findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
            } else {
                Toast.makeText(context, "Please enter Note Title", Toast.LENGTH_LONG).show()
            }
        }

        binding.ivNoteIcon.setOnClickListener { openImagePicker() }
        binding.ivEditIcon.setOnClickListener { openImagePicker() }

    }

    private fun getNote(): NoteModel {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteDetails = binding.etNoteDetails.text.toString()
        val imageBitmap = binding.ivNoteIcon.drawable.toBitmap()
        return NoteModel(0, noteTitle, noteDetails, imageBitmap)
    }

    private fun saveNote() {
        val note = getNote()
        viewModel.insertNote(note)
    }

    private fun openImagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        galleryLauncher.launch(intent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    binding.ivNoteIcon.setImageURI(selectedImageUri)
                }
            }
        }
}