package com.softech.translationapp


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.files_list.view.*
import java.io.File

import com.softech.translationapp.GalleryRecyclerClickListener


class GalleryImageAdapter(private val context: Context, private val mediaOptions: ArrayList<String>): RecyclerView.Adapter<GalleryImageAdapter.MyViewHolder>() {

    var listener: GalleryRecyclerClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.files_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mediaOptions.size
    }

    //Just to make progress bar
    fun proBar(): Drawable{
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        return circularProgressDrawable!!
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        Glide.with(context)
            .load(Uri.fromFile(File(mediaOptions[position])))
            .apply(RequestOptions()
                .placeholder(proBar()))
            .into(holder.imageView)


        holder.imageView.setOnClickListener {
            Log.d("Usman","setOnClickListener: ")
            Log.d("Usman","setOnClickListener: Path ${mediaOptions[position]}")

            listener?.onItemClicked(mediaOptions[position])

            val intent = Intent(context,VideoPlayActivity::class.java)

            intent.putExtra("path",mediaOptions[position].toString())

           context.startActivity(intent)
            Log.d("Usman","setOnClickListener: startActivity ")

        }

        // Picasso.get().load(movieList[position].imageurl).into(holder.image)
//        holder.txt_name.text = movieList[position].name
//        holder.txt_team.text = movieList[position].team
//        holder.createdby.text = movieList[position].createdby
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView
//        var txt_name: TextView
//        var txt_team: TextView
//        var createdby: TextView

        init {
            imageView = itemView.image_view
//            txt_name = itemView.txt_name
//            txt_team = itemView.txt_team
//            createdby = itemView.txt_createdby

        }

    }
}
