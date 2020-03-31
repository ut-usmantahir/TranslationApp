package com.softech.translationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softech.translationapp.Method.load_Directory_Files
import java.io.File


class SplashScreen : AppCompatActivity() {
    private var storage: File? = null
    private lateinit var storagePaths: Array<String>
    public override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)

        //load data here
        storagePaths = StorageUtil.getStorageDirectories(this)
        for (path in storagePaths) {
            storage = File(path)
            load_Directory_Files(storage!!)
        }
        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)
    }
}