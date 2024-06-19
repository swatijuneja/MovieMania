package com.example.movies.screens.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
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
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment(), AdapterCallback {
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieListAdapter
    private lateinit var viewModel: MovieListViewModel
    private var favMovieMap = mapOf<Int, MovieItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Swati", "Fragment onCreate")
        val movieDao = MovieAppBuilder(requireContext()).builder.getDao()
        viewModel = ViewModelProvider(this, MovieViewModelFactory(movieDao))[MovieListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Swati", "Fragment onCreateView")
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        adapter = MovieListAdapter(requireActivity(), true, ClickListener {
            onItemClick(it) }, this)
        binding.recyclerView.adapter = adapter
        populateMovieList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Swati", "Fragment onViewCreated")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Swati", "Fragment onDestroy")
    }

    private fun populateMovieList() {
        runBlocking {
        viewModel.movieList.observe(requireActivity()) { movieList ->
            viewModel.favMovieList.observe(requireActivity()) { favList ->
                Log.d("Swati", "favList ${favList.size}")
                favMovieMap = favList.associateBy { it.id }
            }
            val k = movieList.map { item ->
                favMovieMap[item.id] ?: item
            }.toMutableList()

            Log.d("Swati", "final list  ${k.size}")
            adapter.addList(k)
        }
        }
    }

    private fun onItemClick(item: MovieItemModel) {
        val movieDescriptionFragment = MovieDescriptionFragment()
        movieDescriptionFragment.arguments = Bundle().apply {
            putParcelable("MovieInfo", item)
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