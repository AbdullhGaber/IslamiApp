package com.example.islamiapp.ui.fragments.home.ahadeeth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.islamiapp.databinding.FragmentAhadeethDetailsBinding



class AhadeethDetailsFragment : Fragment() {
    private lateinit var mBinding : FragmentAhadeethDetailsBinding
    private val mNavArgs by navArgs<AhadeethDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentAhadeethDetailsBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI(){
        val hadeeth = mNavArgs.hadeeth
        mBinding.hadeethTitleTv.text = hadeeth.title
        mBinding.hadeethTextTv.text = hadeeth.text
        mBinding.backArrowIv.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}