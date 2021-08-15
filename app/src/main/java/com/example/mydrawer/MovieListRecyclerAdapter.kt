package com.example.mydrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydrawer.databinding.MovieListBinding

class MovieListRecyclerAdapter : RecyclerView.Adapter<MovieListRecyclerAdapter.MyViewHolder>() {

    //var movieList=MovieList

    inner class MyViewHolder(private val binding: MovieListBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(movieData: MovieData){
            binding.titleTextview.text=movieData.movieInfo?.movieNm?:""
            binding.detailsTextView.text=movieData.movieInfo?.audiCnt?:""
            binding.details2TextView.text=movieData.movieDetails?.audits?.get(0)?.watchGradeNm?:""



        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListRecyclerAdapter.MyViewHolder {
        val binding=MovieListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListRecyclerAdapter.MyViewHolder, position: Int) {
        holder.bind(MovieList.data[position])
    }

    override fun getItemCount(): Int =MovieList.data.size

}