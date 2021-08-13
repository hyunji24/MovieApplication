package com.example.mydrawer

import com.example.mydrawer.databinding.MovieCommentItemBinding

interface OnMovieCommentClickListener {

    fun onItemClick(holder:MovieCommentAdapter.ViewHolder?, view: MovieCommentItemBinding, position:Int)
}