package com.softech.translationapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.util.*


object StorageUtil {
    // Primary physical SD-CARD (not emulated)
    private val EXTERNAL_STORAGE = System.getenv("EXTERNAL_STORAGE")

    // All Secondary SD-CARDs (all exclude primary) separated by File.pathSeparator, i.e: ":", ";"
    private val SECONDARY_STORAGES = System.getenv("SECONDARY_STORAGE")

    // Primary emulated SD-CARD
    private val EMULATED_STORAGE_TARGET =
        System.getenv("EMULATED_STORAGE_TARGET")

    // PhysicalPaths based on phone model
    @SuppressLint("SdCardPath")
    private val KNOWN_PHYSICAL_PATHS = arrayOf(
        "/storage/sdcard0",
        "/storage/sdcard1",  //Motorola Xoom
        "/storage/extsdcard",  //Samsung SGS3
        "/storage/sdcard0/external_sdcard",  //User request
        "/mnt/extsdcard",
        "/mnt/sdcard/external_sd",  //Samsung galaxy family
        "/mnt/sdcard/ext_sd",
        "/mnt/external_sd",
        "/mnt/media_rw/sdcard1",  //4.4.2 on CyanogenMod S3
        "/removable/microsd",  //Asus transformer prime
        "/mnt/emmc",
        "/storage/external_SD",  //LG
        "/storage/ext_sd",  //HTC One Max
        "/storage/removable/sdcard1",  //Sony Xperia Z1
        "/data/sdext",
        "/data/sdext2",
        "/data/sdext3",
        "/data/sdext4",
        "/sdcard1",  //Sony Xperia Z
        "/sdcard2",  //HTC One M8s
        "/storage/microsd" //ASUS ZenFone 2
    )

    fun getStorageDirectories(context: Context): Array<String> {
        // Final set of paths
        val availableDirectoriesSet: MutableSet<String?> =  HashSet()

        if (!TextUtils.isEmpty(EMULATED_STORAGE_TARGET)) {
            // Device has an emulated storage
            availableDirectoriesSet.add(emulatedStorageTarget)
        } else {
            // Device doesn't have an emulated storage
            availableDirectoriesSet.addAll(getExternalStorage(context))
        }

//        // Add all secondary storages
//        Collections.addAll(
//            availableDirectoriesSet,
//            *allSecondaryStorages
//        )
        val storagesArray =
            arrayOfNulls<String>(availableDirectoriesSet.size)

//        availableDirectoriesSet.toArray(storagesArray)


        return arrayOf(Arrays.toString(storagesArray))
    }

    private fun getExternalStorage(context: Context): Set<String?> {

        val availableDirectoriesSet: MutableSet<String?> = HashSet()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val files = getExternalFilesDirs(context, null)
            for (file in files) {
                if (file != null) {
                    val applicationSpecificAbsolutePath = file.absolutePath
                    val rootPath = applicationSpecificAbsolutePath.substring(
                        0,
                        applicationSpecificAbsolutePath.indexOf("Android/data")
                    )
                    availableDirectoriesSet.add(rootPath)
                }
            }
        } else {
            if (TextUtils.isEmpty(EXTERNAL_STORAGE)) {
                availableDirectoriesSet.addAll(availablePhysicalPaths)
            } else {
                availableDirectoriesSet.add(EXTERNAL_STORAGE)
            }
        }
        return availableDirectoriesSet
    }

    private val emulatedStorageTarget: String?
        private get() {
            var rawStorageId = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val path =
                    Environment.getExternalStorageDirectory().absolutePath
                val folders =
                    path.split(File.separator).toTypedArray()
                val lastSegment = folders[folders.size - 1]
                if (!TextUtils.isEmpty(lastSegment) && TextUtils.isDigitsOnly(lastSegment)) {
                    rawStorageId = lastSegment
                }
            }
            return if (TextUtils.isEmpty(rawStorageId)) {
                EMULATED_STORAGE_TARGET
            } else {
                EMULATED_STORAGE_TARGET + File.separator + rawStorageId
            }
        }

    private val allSecondaryStorages: Array<String?>
        private get() = if (!TextUtils.isEmpty(SECONDARY_STORAGES)) {
            // All Secondary SD-CARDs split into array
            SECONDARY_STORAGES!!.split(File.pathSeparator).toTypedArray()
        } else arrayOfNulls(0)

    private val availablePhysicalPaths: List<String?>
        private get() {
            val availablePhysicalPaths: MutableList<String?> =
                ArrayList()
            for (physicalPath in KNOWN_PHYSICAL_PATHS) {
                val file = File(physicalPath)
                if (file.exists()) {
                    availablePhysicalPaths.add(physicalPath)
                }
            }
            return availablePhysicalPaths
        }

    private fun getExternalFilesDirs(
        context: Context,
        type: String?
    ): Array<File?> {
        return if (Build.VERSION.SDK_INT >= 19) {
            context.getExternalFilesDirs(type)
        } else {
            arrayOf(context.getExternalFilesDir(type))
        }
    }
}