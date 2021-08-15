package com.example.mydrawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mydrawer.databinding.Fragment1Binding

import com.example.mydrawer.databinding.Fragment2Binding
import com.example.mydrawer.databinding.MovieCommentItemBinding


class Fragment2 : Fragment() {
    var index:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle=arguments
        if(bundle!=null){
            index=bundle.getInt("index")
        }
    }

    private lateinit var binding: Fragment2Binding
    private lateinit var adapter:MovieCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= Fragment2Binding.inflate(inflater, container, false)

        setData(binding.root)

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

    fun setData(rootview: View){
        val movieData=MovieList.data[index]

        val imageId=movieData.tmdbMovieResult?.poster_path
        if(imageId!=null && imageId.isNotEmpty()){
            val url="http://image.tmdb.org/t/p/w200${imageId}"

            Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(binding.thumbnailPoster)
        }

        binding.movieTitleText.text=movieData.movieInfo?.movieNm

        val gradeTitle=movieData.movieDetails?.audits?.get(0)?.watchGradeNm
        var grade=0
        if(gradeTitle!=null){
            if(gradeTitle.indexOf("전체")>-1){
                grade=0
                binding.ratingView.setImageResource(R.drawable.agelimit)
            }
            else{
                grade=1
                binding.ratingView.setImageResource(R.drawable.agelimit)
            } //TODO 연령대랑 사진 수정
        }

        binding.releaseDateText.text=movieData.movieInfo?.openDt
        binding.genreTextView.text=movieData.movieDetails?.genres?.get(0)?.genreNm
        binding.runningTimeTextView.text=movieData.movieDetails?.showTm
        binding.rankingTextView.text="${index+1}위"
        binding.peopleNumTextView.text=movieData.movieInfo?.audiCnt
        binding.directorTextView.text=movieData.movieDetails?.directors?.get(0)?.peopleNm
        binding.actorTextView.text=movieData.movieDetails?.actors?.get(0)?.peopleNm+"("+movieData.movieDetails?.actors?.get(0)?.cast+")"
        binding.companyTextView.text=movieData.movieDetails?.companies?.get(0)?.companyNm
        binding.plotText.text=movieData.tmdbMovieResult?.overview
    }

    companion object {
        private const val TAG="Fragment2"

        fun newInstance(index:Int?):Fragment2{
            val fragment=Fragment2()
            val bundle=Bundle()
            bundle.putInt("index",index!!)
            fragment.arguments=bundle

            return fragment
        }
    }

}