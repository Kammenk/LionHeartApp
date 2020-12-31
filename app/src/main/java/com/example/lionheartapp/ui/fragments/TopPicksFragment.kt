package com.example.lionheartapp.ui.fragments

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.lionheartapp.R
import com.example.lionheartapp.adapters.ViewPagerAdapter
import com.example.lionheartapp.models.PhotoPost
import com.example.lionheartapp.util.Constants
import com.example.lionheartapp.util.Resource
import com.example.lionheartapp.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class TopPicksFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var adapter: PagerAdapter
    private lateinit var postColors: Array<Int>
    private lateinit var argbEvaluator: ArgbEvaluator
    private lateinit var postList: ArrayList<PhotoPost>
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_picks, container, false)
        postList = ArrayList<PhotoPost>(emptyList())
        viewPager = view.findViewById(R.id.viewPager)
        argbEvaluator = ArgbEvaluator()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.getPhotos(1,10, Constants.ACCESS_KEY)

        viewPager.setPadding(50,0,50,0)

        postColors = arrayOf(resources.getColor(R.color.postColor1),resources.getColor(R.color.postColor2),
            resources.getColor(R.color.postColor3),resources.getColor(R.color.postColor4),resources.getColor(R.color.postColor5),resources.getColor(R.color.postColor6),
            resources.getColor(R.color.postColor7),resources.getColor(R.color.postColor8),resources.getColor(R.color.postColor9),resources.getColor(R.color.postColor10))

        mainViewModel.photosResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        adapter = ViewPagerAdapter(it, requireContext())
                        viewPager.adapter = adapter
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

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if(position< (adapter.count - 1) && position < (postColors.size - 1)){
                    viewPager.setBackgroundColor(argbEvaluator.evaluate(positionOffset,postColors[position],postColors[position + 1]) as Int)
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

//    private fun setupViewAdapter() {
//        postList.add(PhotoPost(R.drawable.viewpageritem1,"Winter Morning...","Conor Luddy",R.drawable.creator1))
//        postList.add(PhotoPost(R.drawable.viewpageritem2,"Metallic Adventure...","Alex Azabache",R.drawable.creator2))
//        postList.add(PhotoPost(R.drawable.viewpageritem3,"Liquid Safari...","Nathan Dumlao",R.drawable.creator3))
//        postList.add(PhotoPost(R.drawable.viewpageritem4,"On top of the world...","Michael Henry",R.drawable.creator4))
//        postList.add(PhotoPost(R.drawable.viewpageritem5,"Himalayan peace...","Alexander Andrews",R.drawable.creator5))
//        postList.add(PhotoPost(R.drawable.viewpageritem6,"A lonely walk...","Nicolas Ladino Silva",R.drawable.creator6))
//
//        adapter = ViewPagerAdapter(postList,requireContext())
//        viewPager.adapter = adapter
//        viewPager.setPadding(50,0,50,0)
//
//        postColors = arrayOf(resources.getColor(R.color.postColor1),resources.getColor(R.color.postColor2),
//                resources.getColor(R.color.postColor3),resources.getColor(R.color.postColor4),resources.getColor(R.color.postColor5),resources.getColor(R.color.postColor6))
//
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                if(position< (adapter.count - 1) && position < (postColors.size - 1)){
//                    viewPager.setBackgroundColor(argbEvaluator.evaluate(positionOffset,postColors[position],postColors[position + 1]) as Int)
//                } else {
//                    viewPager.setBackgroundColor(postColors[postColors.size - 1])
//                }
//            }
//
//            override fun onPageSelected(position: Int) {
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//        })
//
//
//    }

}