package com.example.magicpinnoteapp.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.example.magicpinnoteapp.db.entity.NoteModel
import java.io.ByteArrayOutputStream

class NoteUtils {
    companion object {
        fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path =
                MediaStore.Images.Media.insertImage(
                    inContext.contentResolver,
                    inImage,
                    "Title",
                    null
                )
            return Uri.parse(path)
        }


         fun shareNote(context: Context, note: NoteModel) {
            val message = note.noteTitle + "\n" + note.noteDetails
            val pictureUri: Uri? = NoteUtils.getImageUri(context, note.icon)
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri)
            shareIntent.type = "image/png"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(shareIntent, "Share"))
        }

    }
}