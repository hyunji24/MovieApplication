package com.example.mydrawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydrawer.databinding.Fragment2Binding
import com.example.mydrawer.databinding.FragmentMovieListBinding


class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter:MovieListRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentMovieListBinding.inflate(inflater, container, false)


        val adapter=MovieListRecyclerAdapter() //어댑터 객체 만듦
       // adapter.datalist=mDatas //데이터 넣어줌
        binding.MovieRecyclerView.adapter=adapter //리사이클러뷰에 어댑터 연결
       // binding.MovieRecyclerView.layoutManager= LinearLayoutManager(this)
        return binding.root
    }


}