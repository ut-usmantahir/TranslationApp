package com.softech.translationapp.adapter


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softech.translationapp.R
import com.softech.translationapp.VideoPlayActivity
import com.softech.translationapp.databinding.LayoutFilesItemBinding
import com.softech.translationapp.helper.GalleryRecyclerClickListener
import java.io.File


class GalleryImageAdapter(private val context: Context, private val mediaOptions: ArrayList<String>): RecyclerView.Adapter<GalleryImageAdapter.MyViewHolder>() {

    var listener: GalleryRecyclerClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

//        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_files_item,parent,false)

        return MyViewHolder(
            //return itemView
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_files_item, parent, false)
        )
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
            .into(holder.filesListBinding.imageView)


        holder.filesListBinding.imageView.setOnClickListener {
            Log.d("Usman","setOnClickListener: ")
            Log.d("Usman","setOnClickListener: Path ${mediaOptions[position]}")

            listener?.onItemClicked(mediaOptions[position])

            val intent = Intent(context,
                VideoPlayActivity::class.java)

            intent.putExtra("path",mediaOptions[position].toString())

           context.startActivity(intent)
            Log.d("Usman","setOnClickListener: startActivity ")

        }

        // Picasso.get().load(movieList[position].imageurl).into(holder.image)
    }

/*
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView

        init {
            imageView = itemView.image_view

        }

    }
*/
        inner class MyViewHolder(
            val filesListBinding: LayoutFilesItemBinding
        ) : RecyclerView.ViewHolder(filesListBinding.root)


}
