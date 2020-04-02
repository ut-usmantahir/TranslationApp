package com.softech.translationapp

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore


open class GalleryFetcher(
    val context: Context,
    private val loadImagesFromGallery: Boolean,
    private val loadVideosFromGallery: Boolean
) : AsyncTask<Cursor, Void, ArrayList<String>>() {

    override fun doInBackground(vararg params: Cursor?): ArrayList<String> {
        val list = ArrayList<String>()

            list.addAll(getAllShownVideosPath(context))

//        if (loadImagesFromGallery) {
//            list.addAll(getAllShownImagesPath(context))
//        }
//        else
//        {
//            list.addAll(getAllShownVideosPath(context))
//        }
        return list
    }

    fun getAllShownImagesPath(context: Context): ArrayList<String> {
        val listOfAllImages = ArrayList<String>()
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        var absolutePathOfImage: String? = null
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        try {
            val projection =
                arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            cursor = context.contentResolver.query(uri, projection, null, null, null)
            column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                listOfAllImages.add(absolutePathOfImage)
            }
            cursor.close()
        } catch (e: Exception) {

        }

        return listOfAllImages
    }

    fun getAllShownVideosPath(context: Context): ArrayList<String> {
        val listOfAllImages = ArrayList<String>()
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        var absolutePathOfImage: String? = null
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        try {
            val projection =
                arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            cursor = context.contentResolver.query(uri, projection, null, null, null)
            column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                listOfAllImages.add(absolutePathOfImage)
            }
            cursor.close()
        } catch (e: Exception) {

        }

        return listOfAllImages
    }




}