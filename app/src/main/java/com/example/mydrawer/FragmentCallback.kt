package com.example.mydrawer

import android.os.Bundle

interface FragmentCallback {
    //https://youngest-programming.tistory.com/8
    //https://june0122.github.io/2021/05/26/android-bnr-12/
    enum class FragmentItem{
        ITEM1,ITEM2,ITEM3
    }

    fun onFragmentSelected(item:FragmentItem,bundle: Bundle?)
}