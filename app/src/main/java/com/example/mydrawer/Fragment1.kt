package com.example.mydrawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mydrawer.databinding.Fragment1Binding


class Fragment1 : Fragment() {

    private lateinit var binding: Fragment1Binding

    inner class PagerAdapter: FragmentStateAdapter {
        //뷰페이저 + fragment 같이사용
        //https://roadtos7.github.io/android/2020/08/10/Android-ViewPager2.html
        //https://flow9.net/bbs/board.php?bo_table=thisisandroid&wr_id=110
        constructor(fm: FragmentManager, lc: Lifecycle):super(fm,lc)

        override fun getItemCount()=MovieList.data.size

        override fun createFragment(position: Int): Fragment {

            val movieData=MovieList.data[position]

            val imageId=movieData.tmdbMovieResult?.poster_path?:""
            val title=movieData.movieInfo?.movieNm?:""
            val detail1=movieData.movieInfo?.audiCnt?:""
            val detail2=movieData.movieDetails?.audits?.get(0)?.watchGradeNm?:""
            val fragment=PageFragment.newInstance(position,imageId,title,detail1,detail2)


            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=Fragment1Binding.inflate(inflater, container, false)

        binding.chip.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                binding.chip2.isChecked=false
                binding.viewContainer.isVisible=true
                binding.viewContainer2.isVisible=false

                showPager(binding)

            }
        }
        binding.chip2.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                binding.chip.isChecked=false
                binding.viewContainer.isVisible=false
                binding.viewContainer2.isVisible=true

                showList(binding)



            }
        }

        showPager(binding)



        return binding.root
    }

    fun showPager(binding:Fragment1Binding){

        binding.pager.adapter=PagerAdapter(requireActivity().supportFragmentManager,requireActivity().lifecycle) //TODO activity!! -> requireActivity()
        binding.pager.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        binding.pager.clipToPadding=false
        binding.pager.setPadding(150,0,150,0)
        binding.pager.offscreenPageLimit=3

        binding.indicator.setViewPager(binding.pager)
        binding.indicator.createIndicators(MovieList.data.size,0)

        binding.pager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                println("${position+1} 페이지 선택됨")

                binding.indicator.animatePageSelected(position)
            }
        })
    }

    fun showList(binding: Fragment1Binding){
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager


        val adapter = MovieListRecyclerAdapter()

        binding.recyclerView.adapter=adapter

    }
}