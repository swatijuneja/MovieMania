package com.example.movies.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ListLayoutBinding
import com.example.movies.models.MovieItemModel

class MovieListAdapter(private val context: Context, private val isFav: Boolean,
                       private val listener: ClickListener?, private val adapterCallback: AdapterCallback?
) :RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var movies = mutableListOf<MovieItemModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder){
            with(movies[position]) {
                binding.tvDate.text = release_date
                Glide.with(context).load("https://image.tmdb.org/t/p/w185/${movies[position].poster_path}")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.itemImg)
                binding.tvTitle.text = title
            }
        }

        listener?.let { lis ->
            holder.itemView.setOnClickListener {
                lis.onItemClick(movies[position]) // nullable high order func
            }
        }
        //Log.d("Swati", "${movies[position].isFav}")

        holder.binding.myToggleButton.apply {
            if (movies[position].isFav) {
                setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_24))
            } else {
                setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_border_24))
            }
            if (!isFav)
            {
                this.visibility = View.GONE
            }

            setOnClickListener {
                if (isChecked)
                {
                    movies[position].isFav = true
                    setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_24))
                    adapterCallback?.onFavItemClick(movies[position])
                }
                else
                {
                    setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_border_24))
                    adapterCallback?.onClick(movies[position].title)
                }
            }
        }
    }

    private fun deleteItem(position: Int) {
        movies.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: MovieItemModel) {
        movies.add(movies.size, item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<MovieItemModel>) {
        movies = list
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val binding: ListLayoutBinding) :RecyclerView.ViewHolder(binding.root)
}

class ClickListener(val movieItem: (item: MovieItemModel) -> Unit) {
    fun onItemClick(item: MovieItemModel) = movieItem(item)
}

interface AdapterCallback { // high order func
    fun onClick(title: String)
    fun onFavItemClick(item: MovieItemModel)
}