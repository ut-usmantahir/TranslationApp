package com.softech.translationapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.softech.translationapp.databinding.ActivityVideoPlayBinding
import com.softech.translationapp.helper.GalleryRecyclerClickListener
import com.softech.translationapp.viewmodel.MainActivityViewModel


class VideoPlayActivity : AppCompatActivity(),
    GalleryRecyclerClickListener {

    lateinit var mGestureDetector:GestureDetector
     var path: String? = null
    lateinit var mainBinding: ActivityVideoPlayBinding
    lateinit var media: MediaController
    lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_video_play)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_play)
        Log.d("Usman","onCreate, VideoPlayActivity")

        actionBar?.hide()

         model = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

        setPath()

//        /storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20200124-WA0006.mp4
    }

    override fun onItemClicked(array: String) {
        Log.d("Usman","onItemClicked array: $array}")

         path = array
        Log.d("Usman","onItemClicked: ${path}")

    }
    private fun setPath(){

        Log.d("Usman","setPath : ${path}")

        val bundle = intent.extras
        var pathUri = bundle?.get("path")

        model.setPath(pathUri.toString())

        initPlayer()

        mGestureDetector = GestureDetector(this, mGestureListener)

//        mainBinding.vvVideoView.setOnTouchListener(mTouchListener)

        Log.d("Usman","setPath => pathUri : ${pathUri}")
    }
    fun initPlayer(){
        val uri = Uri.parse(model.getPath())

        mainBinding.vvVideoView.setVideoURI(uri)

        media = MediaController(this)

        mainBinding.vvVideoView.setMediaController(media)
        media.setAnchorView(mainBinding.vvVideoView)

        mainBinding.vvVideoView.setKeepScreenOn(true)

//        model.setVideoSeekPosition( mainBinding.vvVideoView.currentPosition)

        if (model.getVideoSeekPosition() > 0) {
            Log.d("Usman","Current Position of Video ${model.getVideoSeekPosition()}")

            mainBinding.vvVideoView.seekTo(model.getVideoSeekPosition())
        } else {
            mainBinding.vvVideoView.seekTo(1);
        }
        mainBinding.vvVideoView.start()

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

    override fun onPause(){
        super.onPause()

        model.setVideoSeekPosition( mainBinding.vvVideoView.currentPosition)
        Log.d("Usman","onPause: Current Position of Video: ${model.getVideoSeekPosition()}")
    }

/*
    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show()
    }
*/

}
