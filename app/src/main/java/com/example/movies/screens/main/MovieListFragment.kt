package com.example.movies.screens.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.data.retrofit.MovieApiClient
import com.example.movies.data.room.MovieAppBuilder
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.models.MovieItemModel
import com.example.movies.screens.AdapterCallback
import com.example.movies.screens.ClickListener
import com.example.movies.screens.MovieListAdapter
import com.example.movies.screens.detail.MovieDescriptionFragment
import com.example.movies.screens.MovieListViewModel
import com.example.movies.screens.MovieViewModelFactory
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
    private var favMovieMap = mapOf<Int, MovieItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        val movieClient = MovieApiClient().movieInterface
        movieBuilder = MovieAppBuilder(requireContext())
        val movieDao = movieBuilder.builder.getDao()
        viewModel = ViewModelProvider(this, MovieViewModelFactory(movieClient, movieDao))[MovieListViewModel::class.java]
        adapter = MovieListAdapter(requireActivity(), true, ClickListener {
            onItemClick(it) }, this)

        binding!!.recyclerView.adapter = adapter
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Swati", "onViewCreated")
        populateMovieList()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun populateMovieList() {
        viewModel.getMovies()
        viewModel.movieList.observe(requireActivity()) { movieList ->
            viewModel.getFavMovie()
            viewModel.favMovieList.observe(requireActivity()) { favList ->
                favMovieMap = favList.associateBy { it.id }
            }

            adapter.addList(movieList.map { item ->
                favMovieMap[item.id] ?: item
            }.toMutableList())
        }
    }

    private fun onItemClick(item: MovieItemModel) {
        val movieDescriptionFragment = MovieDescriptionFragment()
        movieDescriptionFragment.arguments = Bundle().apply {
            putSerializable("MovieInfo", item)
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, movieDescriptionFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(title: String) {
        viewModel.deleteFavMovie(title)
    }

    override fun onFavItemClick(item: MovieItemModel) {
        val favMovieFragment = FavMovieFragment()
        viewModel.saveMovie(item)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, favMovieFragment)
            .addToBackStack(null)
            .commit()
    }

}