package com.example.mydrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mydrawer.databinding.MovieListBinding

class MovieListRecyclerAdapter : RecyclerView.Adapter<MovieListRecyclerAdapter.MyViewHolder>() {

    //var movieList=MovieList

    inner class MyViewHolder(private val binding: MovieListBinding):RecyclerView.ViewHolder(binding.root){



        fun bind(movieData: MovieData){
            val imageId=movieData.tmdbMovieResult?.poster_path?:""
            binding.titleTextview.text=movieData.movieInfo?.movieNm?:""
            binding.detailsTextView.text=movieData.movieInfo?.audiCnt?:""
            binding.details2TextView.text=movieData.movieDetails?.audits?.get(0)?.watchGradeNm?:""
            if(imageId!=null && imageId!!.isNotEmpty()) {
                val url = "http://image.tmdb.org/t/p/w200${imageId}"
                Glide.with(binding.root)
                    .load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(binding.previewImageView)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListRecyclerAdapter.MyViewHolder {
        val binding=MovieListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListRecyclerAdapter.MyViewHolder, position: Int) {
        holder.bind(MovieList.data[position])
    }

    override fun getItemCount(): Int =MovieList.data.size

}