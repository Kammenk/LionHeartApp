package com.example.lionheartapp.ui.fragments

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.lionheartapp.R
import com.example.lionheartapp.adapters.ViewPagerAdapter
import com.example.lionheartapp.api.RemoteAccess
import com.example.lionheartapp.models.PhotoItem
import com.example.lionheartapp.util.Constants
import com.example.lionheartapp.util.Constants.Companion.BASE_URL
import com.example.lionheartapp.util.Constants.Companion.BASE_URL_PHOTOS_EXT
import com.example.lionheartapp.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class TopPicksFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var adapter: PagerAdapter
    private lateinit var postColors: Array<Int>
    private lateinit var argbEvaluator: ArgbEvaluator
    private lateinit var postList: ArrayList<PhotoItem>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var remoteAccess: RemoteAccess

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_picks, container, false)
        postList = ArrayList<PhotoItem>(emptyList())
        viewPager = view.findViewById(R.id.viewPager)
        argbEvaluator = ArgbEvaluator()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.getPhotos("$BASE_URL/$BASE_URL_PHOTOS_EXT", 1, 10, Constants.ACCESS_KEY)

        viewPager.setPadding(50, 0, 50, 0)

        postColors = arrayOf(
            resources.getColor(R.color.postColor1),
            resources.getColor(R.color.postColor2),
            resources.getColor(R.color.postColor3),
            resources.getColor(R.color.postColor4),
            resources.getColor(R.color.postColor5),
            resources.getColor(R.color.postColor6),
            resources.getColor(R.color.postColor7),
            resources.getColor(R.color.postColor8),
            resources.getColor(R.color.postColor9),
            resources.getColor(R.color.postColor10)
        )

        remoteAccess = RemoteAccess()
        GlobalScope.launch(Dispatchers.IO) {
            var response = remoteAccess.getPhotos("$BASE_URL", 2, 10, Constants.ACCESS_KEY)
            withContext(Dispatchers.Main) {
                adapter = ViewPagerAdapter(response, requireContext())
                viewPager.adapter = adapter

            }
        }

//        mainViewModel.photosResponse.observe(viewLifecycleOwner, { response ->
//            adapter = ViewPagerAdapter(response, requireContext())
//            viewPager.adapter = adapter
//        })

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

        return view
    }

}