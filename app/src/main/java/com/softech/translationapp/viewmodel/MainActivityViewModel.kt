package com.softech.translationapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private lateinit var videoPath: String
    private var videoSeekPosition: Int = 1


    init {
        Log.d("Usman","MainActivityViewModel")
    }
    fun setPath(uri: String){
        videoPath = uri
    }
    fun getPath(): String{
        return videoPath
    }
    fun setVideoSeekPosition(seekPos: Int){
        videoSeekPosition = seekPos
    }
    fun getVideoSeekPosition(): Int {
        return videoSeekPosition
    }
}