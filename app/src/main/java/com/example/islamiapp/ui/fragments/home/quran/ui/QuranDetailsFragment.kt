package com.example.islamiapp.ui.fragments.home.quran.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.islamiapp.databinding.FragmentQuarnDetailsBinding

class QuranDetailsFragment : Fragment() {
    private lateinit var mBinding : FragmentQuarnDetailsBinding
    private val args : QuranDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentQuarnDetailsBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun getVersesList() : List<String>{
        val allSurahContent = activity?.assets?.open("${args.surahPosition + 1}.txt")
            ?.bufferedReader().use{
                it?.readText()
            }

        return allSurahContent!!.split("\n")
    }

    private fun bindUI(){

        val surahContent = getVersesList().mapIndexed { index, string ->
            "$string(${index + 1})"
        }.joinToString(separator = " ")

       mBinding.surahNameTv.text = args.surahTitle
       mBinding.surahVersesTv.text = surahContent
    }

}