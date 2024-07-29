package com.example.islamiapp.ui.fragments.home.quran.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamiapp.R
import com.example.islamiapp.data.models.surahs
import com.example.islamiapp.databinding.FragmentQuranBinding
import com.example.islamiapp.ui.fragments.home.quran.adapters.SurahsRVAdapter


class QuranFragment : Fragment() {
    private lateinit var mBinding : FragmentQuranBinding
    private val mSurahsAdapter by lazy {
        SurahsRVAdapter(
            onItemClick = { title , index ->
                val action = QuranFragmentDirections.actionQuranFragmentToQuranDetailsFragment(
                    surahPosition = index,
                    surahTitle = title
                )
                findNavController().navigate(action)
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentQuranBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRV()
        populateDataInRV()
    }

    private fun setUpRV(){
        mBinding.surahsRv.apply {
            adapter = mSurahsAdapter
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        }
    }

    private fun populateDataInRV(){
        mSurahsAdapter.listDiffer.submitList(surahs)
    }
}