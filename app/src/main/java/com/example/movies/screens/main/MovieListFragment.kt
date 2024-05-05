package com.example.movies.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movies.data.retrofit.MovieApiClient
import com.example.movies.databinding.FragmentMovieListBinding
import com.example.movies.models.MovieItemModel

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment() {
    private var binding: FragmentMovieListBinding? = null
    private lateinit var adapter: MovieListAdapter
    private lateinit var viewModel: MovieListViewModel
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
        viewModel = ViewModelProvider(this, MovieViewModelFactory(movieClient))[MovieListViewModel::class.java]
        viewModel.getMovies()
        viewModel.movieList.observe(this.requireActivity()) {
            adapter = MovieListAdapter(this.requireActivity(), it)
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

}