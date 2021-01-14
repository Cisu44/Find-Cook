package com.example.findcook.forLater

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.findcook.R
import com.example.findcook.home.HomeViewModel

class ForLaterFragment : Fragment() {

    private val forLaterViewModel by viewModels<ForLaterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.for_later_fragment, container, false)
    }


}