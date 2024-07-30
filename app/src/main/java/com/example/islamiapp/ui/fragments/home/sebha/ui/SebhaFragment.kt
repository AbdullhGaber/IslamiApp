package com.example.islamiapp.ui.fragments.home.sebha.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.islamiapp.R
import com.example.islamiapp.databinding.FragmentSebhaBinding


class SebhaFragment : Fragment() {
    private lateinit var mBinding : FragmentSebhaBinding
    private val mAzkar by lazy {
        listOf(
            getString(R.string.sobhan_allah),
            getString(R.string.al_hamdo_llellah),
            getString(R.string.allah_akaber),
            getString(R.string.la_elaah_ela_allah),
        )
    }

    private var azkarIdx = 0

    private var azkarCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentSebhaBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI(){
            mBinding.tasbeehCount.text = azkarCounter.toString()
            mBinding.sobhanAllahBtn.text = mAzkar[0]
            mBinding.sobhanAllahBtn.setOnClickListener {
                rotateSebha()
                updateSebhaCount()
                if(azkarCounter % 33 == 0 && azkarCounter != 0)
                    updateTasbeehText()
            }
    }

    private fun rotateSebha(){
        mBinding.sebhaIv.rotation += 90f
    }

    private fun updateSebhaCount(){
        mBinding.tasbeehCount.text = ((++azkarCounter) % 33).toString()
    }

    private fun updateTasbeehText(){
        mBinding.sobhanAllahBtn.text = mAzkar[(++azkarIdx) % 4]
    }
}