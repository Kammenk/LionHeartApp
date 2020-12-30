package com.example.lionheartapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lionheartapp.R
import com.example.lionheartapp.adapters.PhotoAdapter
import com.example.lionheartapp.models.PhotosItem
import com.example.lionheartapp.models.Urls
import com.example.lionheartapp.util.Constants.Companion.ACCESS_KEY
import com.example.lionheartapp.util.Resource
import com.example.lionheartapp.viewmodels.MainViewModel

class PhotoListFragment : Fragment() {

    private lateinit var photosAdapter: PhotoAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var photosRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.photoRecyclerView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)

        photosRecyclerView = view.findViewById(R.id.photoRecyclerView)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.getPhotos(1,20,ACCESS_KEY)

            mainViewModel.photosResponse.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            photosAdapter.setData(it)
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(
                            requireContext(),
                            "loading",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

        setupRecyclerView()
        return view
    }

    private fun setupRecyclerView() {
        photosAdapter = PhotoAdapter()
        photosRecyclerView.apply {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }
    }

}