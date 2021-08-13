package com.example.mydrawer

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mydrawer.databinding.FragmentPageBinding

class PageFragment : Fragment() {

    private lateinit var binding: FragmentPageBinding

    //var index:Int=0
    var imageId : String? = null
    var title: String? = null
    var details1: String? = null
    var details2: String? = null

    var callback: FragmentCallback? = null





    override fun onAttach(context: Context) {//액티비티에 추가될 때 호출되어 액티비티를 받아오는 콜백 함수 onAttach

        super.onAttach(context)
        //callback=context as FragmentCallback?
    if(context is FragmentCallback)
    {
        callback = context
    }else
    {
        Log.d(TAG, "Activity not FragmentCallback")
    }
}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle=arguments
        if(bundle!=null){
            //index=bundle.getInt("index",0)
            imageId=bundle.getString("imageId")
            title=bundle.getString("title")
            details1=bundle.getString("details1")
            details2=bundle.getString("details2")
        }
    }

    override fun onDetach() {
        super.onDetach()

        if (callback != null) {
            callback = null
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_page, container, false)
        binding= FragmentPageBinding.inflate(inflater,container,false)

        if(imageId!=null && imageId!!.isNotEmpty()) {
            val url="http://image.tmdb.org/t/p/w200${imageId}"
            Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(binding.posterImageView)
        }
        binding.titleTextview.text=title
        binding.detailsTextView.text=details1
        binding.details2TextView.text=details2

        binding.detailsButton.setOnClickListener {
            if(callback!=null){
                val bundle=Bundle()
                bundle.putInt("index",0)

                callback!!.onFragmentSelected(FragmentCallback.FragmentItem.ITEM2,bundle)
            }
            else Log.d(TAG, "null FragmentCallback")
        }

        return binding.root
    }



    companion object {
        private const val TAG = "PageFragment"


        fun newInstance(
            imageId: String?,
            title: String?,
            details1: String?,
            details2: String?
        ): PageFragment {
            val fragment = PageFragment()

            val bundle = Bundle()
            bundle.putString("imageId", imageId)
            bundle.putString("title", title)
            bundle.putString("details1", details1)
            bundle.putString("details2", details2)
            fragment.arguments = bundle

            return fragment
        }
    }


    }
