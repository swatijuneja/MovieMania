package com.example.movies.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.MainActivity
import com.example.movies.R
import com.example.movies.databinding.ListLayoutBinding
import com.example.movies.models.MovieItemModel
import com.example.movies.models.MovieModel
import com.example.movies.screens.detail.MovieDescriptionFragment

class MovieListAdapter(private val context: Context, private val movies: List<MovieItemModel>) :RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

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
            val activity = context as MainActivity
            val movieDescriptionFragment = MovieDescriptionFragment()
            movieDescriptionFragment.arguments = Bundle().apply {
                putParcelable("MovieInfo", movies[position])
            }
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, movieDescriptionFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    inner class MovieViewHolder(val binding: ListLayoutBinding) :RecyclerView.ViewHolder(binding.root)
}