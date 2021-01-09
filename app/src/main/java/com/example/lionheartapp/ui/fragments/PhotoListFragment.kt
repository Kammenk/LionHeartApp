package com.example.lionheartapp.ui.fragments

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lionheartapp.R
import com.example.lionheartapp.adapters.PhotoAdapter
import com.example.lionheartapp.api.RemoteAccess
import com.example.lionheartapp.util.Constants.Companion.ACCESS_KEY
import com.example.lionheartapp.util.Constants.Companion.BASE_CATEGORIES_URL
import com.example.lionheartapp.util.Constants.Companion.BASE_URL
import com.example.lionheartapp.util.SnackBar
import com.example.lionheartapp.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random

class PhotoListFragment : Fragment(), View.OnClickListener {

    //Variables
    private lateinit var photosAdapter: PhotoAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var remoteAccess: RemoteAccess
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var noWifiImage: ImageView
    private lateinit var noWifiTextView: TextView

    //Carousel items
    private lateinit var firstItem: TextView
    private lateinit var secondItem: TextView
    private lateinit var thirdItem: TextView
    private lateinit var fourthItem: TextView
    private lateinit var fifthItem: TextView

    //Snackbar
    private lateinit var snackBar: SnackBar

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

        //Used when using other threads
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //Hiding/showing the bottom navigation
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        if (bottomNavigationView.visibility != View.VISIBLE) {
            bottomNavigationView.visibility =
                View.VISIBLE
        }

        photosRecyclerView = view.findViewById(R.id.photoRecyclerView)
        remoteAccess = RemoteAccess()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        firstItem = view.findViewById(R.id.carouselTitleOne)
        secondItem = view.findViewById(R.id.carouselTitleTwo)
        thirdItem = view.findViewById(R.id.carouselTitleThree)
        fourthItem = view.findViewById(R.id.carouselTitleFour)
        fifthItem = view.findViewById(R.id.carouselTitleFive)
        noWifiImage = view.findViewById(R.id.noWifiImage)
        noWifiTextView = view.findViewById(R.id.noWifiText)

        snackBar = SnackBar()

        firstItem.setOnClickListener(this)
        secondItem.setOnClickListener(this)
        thirdItem.setOnClickListener(this)
        fourthItem.setOnClickListener(this)
        fifthItem.setOnClickListener(this)

        mainViewModel.getPhotos(BASE_URL, 1, 20, ACCESS_KEY)

        //Checking if there is any new data and filling the recyclerview with it
        mainViewModel.photosResponse.observe(viewLifecycleOwner, { response ->
            photosAdapter.setData(response)
        })

        setupRecyclerView()

        //If there is no internet connection we show snackbar and other notifications
        if (!mainViewModel.hasInternetConnection()) {
            noWifiImage.visibility = View.VISIBLE
            noWifiTextView.visibility = View.VISIBLE
            snackBar.showSnackbar(
                requireActivity().findViewById(R.id.mainConstraintLayout),
                requireContext()
            )
        }
        return view
    }

    private fun setupRecyclerView() {
        //Setting up recyclerview
        photosAdapter = PhotoAdapter()
        photosRecyclerView.apply {
            adapter = photosAdapter
            this.setItemViewCacheSize(20)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        photosRecyclerView.setHasFixedSize(true)
    }

    override fun onClick(p0: View?) {
        //Generating new images for specific categories
        if (mainViewModel.hasInternetConnection()) {
            noWifiImage.visibility = View.GONE
            noWifiTextView.visibility = View.GONE
        }
        when (p0!!.id) {
            R.id.carouselTitleOne -> {
                mainViewModel.getCategories(
                    BASE_CATEGORIES_URL,
                    "nature",
                    Random.nextInt(0, 4),
                    20,
                    ACCESS_KEY
                )
            }
            R.id.carouselTitleTwo -> {
                mainViewModel.getCategories(
                    BASE_CATEGORIES_URL,
                    "people",
                    Random.nextInt(0, 4),
                    20,
                    ACCESS_KEY
                )
            }
            R.id.carouselTitleThree -> {
                mainViewModel.getCategories(
                    BASE_CATEGORIES_URL,
                    "current-events",
                    Random.nextInt(0, 4),
                    20,
                    ACCESS_KEY
                )
            }
            R.id.carouselTitleFour -> {
                mainViewModel.getCategories(
                    BASE_CATEGORIES_URL,
                    "fashion",
                    Random.nextInt(0, 4),
                    20,
                    ACCESS_KEY
                )
            }
            R.id.carouselTitleFive -> {
                mainViewModel.getCategories(
                    BASE_CATEGORIES_URL,
                    "film",
                    Random.nextInt(0, 4),
                    20,
                    ACCESS_KEY
                )
            }
        }
    }
}