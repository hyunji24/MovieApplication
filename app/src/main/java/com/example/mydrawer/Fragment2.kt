package com.example.mydrawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydrawer.databinding.Fragment1Binding

import com.example.mydrawer.databinding.Fragment2Binding
import com.example.mydrawer.databinding.MovieCommentItemBinding


class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding
    private lateinit var adapter:MovieCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= Fragment2Binding.inflate(inflater, container, false)

        val layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.recyclerview.layoutManager=layoutManager

        adapter= MovieCommentAdapter()

        adapter.items.add(MovieComment("24hyu***","5.0","인생 최고의 영화"))
        adapter.items.add(MovieComment("24hyu***","4.5","디카프리오와 케이트윈슬렛 연기대박"))

        binding.recyclerview.adapter=adapter

        adapter.listener=object:OnMovieCommentClickListener{//TODO 맞는지 모르겠
            override fun onItemClick(
                holder: MovieCommentAdapter.ViewHolder?,
                view: MovieCommentItemBinding,
                position: Int
            ) {
                val name=adapter.items[position]
                Toast.makeText(context,"아이템 선택됨", Toast.LENGTH_LONG).show()
            }
        }


        return binding.root
    }

}