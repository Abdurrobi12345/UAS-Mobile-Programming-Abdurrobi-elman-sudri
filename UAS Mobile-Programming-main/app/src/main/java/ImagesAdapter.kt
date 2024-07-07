package com.abdurrobi.loginactivity

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImagesAdapter(private val context: Context, private var images: MutableList<ByteArray>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        holder.imageView.setImageBitmap(bitmap)

        holder.deleteButton.setOnClickListener {
            // Menghapus gambar dari database
            val dbHelper = DatabaseHelper(context)
            dbHelper.deletePhoto(image)

            // Menghapus gambar dari daftar dan memperbarui tampilan
            images.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, images.size)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
