package com.softech.translationapp


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


 class RecyclerViewAdapter internal constructor(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.files_list, parent, false)
        return FileLayoutHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FileLayoutHolder)

        val uri =
            Uri.fromFile(Constant.allMediaList[position])
        Glide.with(mContext)
            .load(uri).thumbnail(0.1f)
            .into((holder as FileLayoutHolder).thumbnail)
    }

    fun itemCount(): Int {
        return Constant.allMediaList.size
    }

    internal inner class FileLayoutHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var thumbnail: ImageView

        init {
            thumbnail = itemView.findViewById(R.id.thumbnail)

        }
    }

     override fun getItemCount(): Int {
         return Constant.allMediaList.size
     }

 }