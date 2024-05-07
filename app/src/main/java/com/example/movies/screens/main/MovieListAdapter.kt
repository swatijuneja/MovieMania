package com.example.movies.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.MainActivity
import com.example.movies.R
import com.example.movies.databinding.ListLayoutBinding
import com.example.movies.models.FavoriteMovie
import com.example.movies.models.MovieItemModel
import com.example.movies.screens.detail.MovieDescriptionFragment
import com.example.movies.screens.favorite.FavMovieFragment

class MovieListAdapter(private val context: Context, private val movies: List<MovieItemModel>,
                       private val listener: ClickListener, private val adapterCallback: AdapterCallback) :RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

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

        holder.itemView.setOnClickListener {
           adapterCallback.onItemClick(movies[position])
        }

        holder.binding.myToggleButton.apply {
            isChecked = false
            setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_border_24))
            setOnClickListener {
                if (!isChecked)
                {
                    isChecked = true
                    setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_24))
                    adapterCallback.onFavItemClick(movies[position])
                }
                else
                {
                    isChecked = false
                    setBackgroundDrawable(getDrawable(context, R.drawable.baseline_favorite_border_24))
                    listener.onClick(movies[position].title)
                }
            }
        }
    }

    inner class MovieViewHolder(val binding: ListLayoutBinding) :RecyclerView.ViewHolder(binding.root)
}

class ClickListener(val movieTitle: (title: String) -> Unit) {
    fun onClick(title: String) = movieTitle(title)
}

interface AdapterCallback {
    fun onItemClick(item: MovieItemModel)
    fun onFavItemClick(item: MovieItemModel)
}