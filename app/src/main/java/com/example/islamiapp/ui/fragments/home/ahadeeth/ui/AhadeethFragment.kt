package com.example.islamiapp.ui.fragments.home.ahadeeth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamiapp.data.models.Hadeeth
import com.example.islamiapp.databinding.FragmentAhadeethBinding
import com.example.islamiapp.ui.fragments.home.ahadeeth.adapters.AhadeethRVAdapter


class AhadeethFragment : Fragment() {
    private lateinit var mBinding : FragmentAhadeethBinding
    private val mAhadeethRVAdapter by lazy {
        AhadeethRVAdapter(
            onItemClick = {
                val action = AhadeethFragmentDirections.actionAhadeethFragmentToAhadeethDetailsFragment(
                    hadeeth = it
                )
                findNavController().navigate(action)
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentAhadeethBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        setUpAhadeethRV()
        populateDataInRVAdapter()
    }

    private fun setUpAhadeethRV(){
        mBinding.ahadeethRv.apply{
            adapter = mAhadeethRVAdapter
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
        }
    }
    private fun populateDataInRVAdapter(){
        mAhadeethRVAdapter.listDiffer.submitList(readAhadeethFile())
    }

    private fun readAhadeethFile() : MutableList<Hadeeth> {
        val hadeeths = mutableListOf<Hadeeth>()

        val allContent = mBinding.root.context.assets?.open("ahadeth.txt")
            ?.bufferedReader().use{
                it?.readText()
            }

        val ahadeethList = allContent?.split("#")

        ahadeethList?.forEachIndexed {index , hadeeth ->
            val hadeethLines = hadeeth.trim().split("\n").toMutableList()
            val title = hadeethLines[0]
            hadeethLines.removeAt(0)
            val hadeeth = Hadeeth(
                id = index + 1,
                title = title,
                text = hadeethLines.joinToString()
            )

            hadeeths.add(hadeeth)
        }

        return hadeeths
    }
}