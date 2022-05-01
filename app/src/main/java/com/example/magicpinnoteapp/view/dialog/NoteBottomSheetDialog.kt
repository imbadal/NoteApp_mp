package com.example.magicpinnoteapp.view.dialog

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.magicpinnoteapp.databinding.NoteBottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NoteBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: NoteBottomSheetLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoteBottomSheetLayoutBinding.inflate(inflater, container, false)
        initClickListener()
        setData(arguments)
        return binding.root
    }

    private fun setData(arguments: Bundle?) {
        arguments?.let {
            val title = it.getString("title") ?: ""
            val details = it.getString("details") ?: ""
            val byteArray = it.getByteArray("icon")
            var iconUri: Bitmap? = null
            byteArray?.let {
                iconUri = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
            binding.tvTitle.text = title
            binding.tvDetails.text = details
            iconUri?.let {
                binding.ivNoteIcon.setImageBitmap(iconUri)
            }
        }
    }

    private fun initClickListener() {
        binding.tvClose.setOnClickListener {
            dismiss()
        }

    }
}