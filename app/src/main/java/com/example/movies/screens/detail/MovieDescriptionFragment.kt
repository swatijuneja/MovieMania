package com.example.movies.screens.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieDescriptionBinding
import com.example.movies.models.MovieItemModel

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentMovieDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDescriptionBinding.inflate(layoutInflater, container, false)
        val movieItem = requireArguments().getParcelable<MovieItemModel>("MovieInfo")!!
        loadMovie(movieItem)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadMovie(item: MovieItemModel) {
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w185/${item.poster_path}")
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView2)

        binding.apply {
            movieTitle.text = item.title
            movieDate.text = item.release_date
            description.text = item.overview
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}