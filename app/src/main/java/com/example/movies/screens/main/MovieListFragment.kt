package com.example.movies.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.data.retrofit.MovieApiClient
import com.example.movies.data.room.MovieAppBuilder
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.models.FavoriteMovie
import com.example.movies.models.MovieItemModel
import com.example.movies.screens.detail.MovieDescriptionFragment
import com.example.movies.screens.favorite.FavMovieFragment

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment(), AdapterCallback {
    private var binding: FragmentMovieListBinding? = null
    private lateinit var adapter: MovieListAdapter
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieBuilder: MovieAppBuilder
    private var list = emptyList<MovieItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        val movieClient = MovieApiClient().movieInterface
        val movieDao = movieBuilder.builder.getDao()
        viewModel = ViewModelProvider(this, MovieViewModelFactory(movieClient, movieDao))[MovieListViewModel::class.java]
        viewModel.getMovies()
        viewModel.movieList.observe(this.requireActivity()) {
            adapter = MovieListAdapter(this.requireActivity(), it, ClickListener {
                viewModel.deleteFavMovie(it) }, this)
            binding!!.recyclerView.adapter = adapter }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onItemClick(item: MovieItemModel) {
        val movieDescriptionFragment = MovieDescriptionFragment()
        movieDescriptionFragment.arguments = Bundle().apply {
            putParcelable("MovieInfo", item)
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, movieDescriptionFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onFavItemClick(item: MovieItemModel) {
        val favMovie = FavoriteMovie(true, item)
        val favMovieFragment = FavMovieFragment()
        favMovieFragment.arguments = Bundle().apply {
            putParcelable("FavMovie", favMovie)
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, favMovieFragment)
            .addToBackStack(null)
            .commit()
    }

}