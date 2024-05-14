package com.example.movies.screens.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movies.data.room.MovieAppBuilder
import com.example.movies.databinding.FragmentFavMovieBinding
import com.example.movies.models.MovieItemModel
import com.example.movies.screens.AdapterCallback
import com.example.movies.screens.MovieListAdapter
import com.example.movies.screens.MovieListViewModel
import com.example.movies.screens.MovieViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [FavMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavMovieFragment : Fragment() {

    private lateinit var binding: FragmentFavMovieBinding
    private lateinit var adapter: MovieListAdapter
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieBuilder: MovieAppBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavMovieBinding.inflate(layoutInflater, container, false)
        movieBuilder = MovieAppBuilder(requireContext())
        val movieDao = movieBuilder.builder.getDao()
        viewModel = ViewModelProvider(this, MovieViewModelFactory(null, movieDao))[MovieListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieListAdapter(this.requireActivity(), false, null, null)
        viewModel.getFavMovie()
        populateFavMovie()
        binding.recyclerView.adapter = adapter
    }

    private fun populateFavMovie() {
        viewModel.favMovieList.observe(requireActivity()) {
             adapter.addList(it)
        }
    }

}