package com.softech.translationapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.softech.translationapp.databinding.ActivityVideoPlayBinding


class VideoPlayActivity : AppCompatActivity(), GalleryRecyclerClickListener {

    lateinit var mGestureDetector:GestureDetector
     var path: String? = null
    lateinit var mainBinding: ActivityVideoPlayBinding
    lateinit var media: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_video_play)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_play)
        Log.d("Usman","VideoPlayActivity :onCreate")

        actionBar?.hide()


        setPath()
//        getPath()
//        /storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20200124-WA0006.mp4
    }

    override fun onItemClicked(array: String) {
        Log.d("Usman","onItemClicked array: $array}")

         path = array
        Log.d("Usman","onItemClicked: ${path}")

//        setPath()
    }
    private fun setPath(){
        Log.d("Usman","setPath : ${path}")

        val bundle = intent.extras
        var pathUri = bundle?.get("path")

        mainBinding.vvVideoView.setVideoURI(Uri.parse(pathUri.toString()))

        media = MediaController(this)

        mainBinding.vvVideoView.setMediaController(media)
        media.setAnchorView(mainBinding.vvVideoView)

        mainBinding.vvVideoView.setKeepScreenOn(true)
        mainBinding.vvVideoView.start()
           mainBinding.vvVideoView.seekTo( 1 )

        mGestureDetector = GestureDetector(this, mGestureListener)

//        mainBinding.vvVideoView.setOnTouchListener(mTouchListener)

        Log.d("Usman","setPath => pathUri : ${pathUri}")

    }
    val mTouchListener = OnTouchListener { v, event ->
        mGestureDetector.onTouchEvent(event)
        true
    }

    val mGestureListener: SimpleOnGestureListener = object : SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (mainBinding.vvVideoView.isPlaying())
                mainBinding.vvVideoView.pause()
            else
                mainBinding.vvVideoView.start()
            return true
        }
    }
/*

    fun getPath(){
        Log.d("Usman","getPath: ${path}")

        mainBinding.vvVideoView.setVideoURI(path as Uri)
    }
*/

}
