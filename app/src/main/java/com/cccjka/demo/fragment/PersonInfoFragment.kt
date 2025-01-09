package com.cccjka.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cccjka.demo.R
import com.cccjka.demo.databinding.FragmentPersoninfoBinding

class PersonInfoFragment: Fragment() {

    private lateinit var viewBinding: FragmentPersoninfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentPersoninfoBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}