package com.example.mydrawer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydrawer.databinding.MovieCommentItemBinding

class MovieCommentAdapter : RecyclerView.Adapter<MovieCommentAdapter.ViewHolder>() {

    var items=mutableListOf<MovieComment>()

    lateinit var listener:OnMovieCommentClickListener

    inner class ViewHolder(private val binding:MovieCommentItemBinding):RecyclerView.ViewHolder(binding.root){

        init{
            binding.contentsTextView.setOnClickListener {
                listener?.onItemClick(this,binding,adapterPosition) //binding..? 434pg보기
            }
        }
        fun bind(movieData:MovieComment){
            binding.idTextView.text=movieData.id
            binding.idTextView.setTextColor(Color.parseColor("#FFBA5F"))
            binding.contentsTextView.text=movieData.contents
            binding.contentsTextView.setTextColor(Color.parseColor("#FFBA5F"))
            binding.ratingBar.rating= (movieData.rating?.toFloat()?:0.0f)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=MovieCommentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount()=items.size


}
