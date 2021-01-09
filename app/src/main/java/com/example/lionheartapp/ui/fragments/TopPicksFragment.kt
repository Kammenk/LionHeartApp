package com.example.lionheartapp.ui.fragments

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.lionheartapp.R
import com.example.lionheartapp.adapters.ViewPagerAdapter
import com.example.lionheartapp.api.RemoteAccess
import com.example.lionheartapp.models.PhotoItem
import com.example.lionheartapp.util.Constants
import com.example.lionheartapp.util.Constants.Companion.BASE_URL
import com.example.lionheartapp.util.SnackBar
import com.example.lionheartapp.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

import java.util.ArrayList

class TopPicksFragment : Fragment() {

    //Variables
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: PagerAdapter
    private lateinit var postColors: Array<Int>
    private lateinit var argbEvaluator: ArgbEvaluator
    private lateinit var postList: ArrayList<PhotoItem>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var remoteAccess: RemoteAccess
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var snackBar: SnackBar
    private lateinit var noWifiImage: ImageView
    private lateinit var noWifiTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_picks, container, false)

        //Hiding/showing bottom navigation
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        if (bottomNavigationView.visibility != View.VISIBLE) {
            bottomNavigationView.visibility =
                View.VISIBLE
        }

        postList = ArrayList<PhotoItem>(emptyList())
        viewPager = view.findViewById(R.id.viewPager)
        remoteAccess = RemoteAccess()
        argbEvaluator = ArgbEvaluator()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.getPhotos(BASE_URL, 2, 10, Constants.ACCESS_KEY)

        //Viewpager customization
        viewPager.setPadding(50, 0, 50, 0)

        //Used to provide colors for the generated images
        postColors = arrayOf(
            ContextCompat.getColor(requireContext(), R.color.postColor1),
            ContextCompat.getColor(requireContext(), R.color.postColor2),
            ContextCompat.getColor(requireContext(), R.color.postColor3),
            ContextCompat.getColor(requireContext(), R.color.postColor4),
            ContextCompat.getColor(requireContext(), R.color.postColor5),
            ContextCompat.getColor(requireContext(), R.color.postColor6),
            ContextCompat.getColor(requireContext(), R.color.postColor7),
            ContextCompat.getColor(requireContext(), R.color.postColor8),
            ContextCompat.getColor(requireContext(), R.color.postColor9),
            ContextCompat.getColor(requireContext(), R.color.postColor10),
        )

        snackBar = SnackBar()
        noWifiImage = view.findViewById(R.id.noWifiImage)
        noWifiTextView = view.findViewById(R.id.noWifiText)

        mainViewModel.photosResponse.observe(viewLifecycleOwner, { response ->
            noWifiImage.visibility = View.GONE
            noWifiTextView.visibility = View.GONE
            adapter = ViewPagerAdapter(response, requireContext())
            viewPager.adapter = adapter
        })

        //On swipe we get a new color and apply it
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position < (adapter.count - 1) && position < (postColors.size - 1)) {
                    viewPager.setBackgroundColor(
                        argbEvaluator.evaluate(
                            positionOffset,
                            postColors[position],
                            postColors[position + 1]
                        ) as Int
                    )
                } else {
                    viewPager.setBackgroundColor(postColors[postColors.size - 1])
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        //if there is no internet we display the snackbar and other notifications
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
}