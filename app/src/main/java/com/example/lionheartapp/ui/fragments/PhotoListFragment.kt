package com.example.lionheartapp.ui.fragments

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lionheartapp.R
import com.example.lionheartapp.adapters.PhotoAdapter
import com.example.lionheartapp.api.RemoteAccess
import com.example.lionheartapp.util.Constants.Companion.ACCESS_KEY
import com.example.lionheartapp.util.Constants.Companion.BASE_URL
import com.example.lionheartapp.viewmodels.MainViewModel

class PhotoListFragment : Fragment() {

    private lateinit var photosAdapter: PhotoAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var remoteAccess: RemoteAccess

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

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        photosRecyclerView = view.findViewById(R.id.photoRecyclerView)
        remoteAccess = RemoteAccess()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.getPhotos(BASE_URL, 1, 20, ACCESS_KEY)

        mainViewModel.photosResponse.observe(viewLifecycleOwner, { response ->
            photosAdapter.setData(response)
        })

        setupRecyclerView()
        return view
    }

    private fun setupRecyclerView() {
        photosAdapter = PhotoAdapter()
        photosRecyclerView.apply {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }
}