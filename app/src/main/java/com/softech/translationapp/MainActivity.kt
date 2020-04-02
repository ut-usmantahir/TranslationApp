package com.softech.translationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PermissionListener {

//    lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//         rxPermissions = RxPermissions(this);

//            init()
        requestPermissions()
    }


/*
    private fun init() {

        rxPermissions
            .request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .subscribe { granted ->
                if (granted) {
                        initVideoFetcher()
                } else {
                    Toast.makeText(
                        this,
                        "Permission required to use gallery storage",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
    }
*/

    private fun requestPermissions(){

        val permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).toString()
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(this)
            .check()
    }


    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        initVideoFetcher()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
        Toast.makeText(
            this,
            "Permission onPermissionRationaleShouldBeShown",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        showSettingsDialog()
        Toast.makeText(
            this,
            "Permission required to use gallery storage",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }



    private fun setList(mediaOptions: ArrayList<String>) {
        val gridLayoutManager = GridLayoutManager(
            applicationContext,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvGallery.layoutManager = gridLayoutManager
        rvGallery.adapter = GalleryImageAdapter(this, mediaOptions)
    }


    private fun initImageFetcher() {
        val imageFetcher = @SuppressLint("StaticFieldLeak")
        object : GalleryFetcher(this, true, false) {
            override fun onPostExecute(paths: ArrayList<String>) {
                super.onPostExecute(paths)
                val mediaOptions = ArrayList<String>()

                mediaOptions.addAll(paths)
                setList(mediaOptions)
            }
        }
        imageFetcher.execute()
    }



    private fun initVideoFetcher() {
        val videoFetcher = @SuppressLint("StaticFieldLeak")
        object : GalleryFetcher(this, false, true) {
            override fun onPostExecute(paths: ArrayList<String>) {
                super.onPostExecute(paths)
                val mediaOptions = ArrayList<String>()

                mediaOptions.addAll(paths)
                setList(mediaOptions)
            }
        }
        videoFetcher.execute()
    }

}