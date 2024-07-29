package com.example.islamiapp.ui.fragments.home.quran.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.islamiapp.data.models.Surah
import com.example.islamiapp.databinding.SurahItemBinding

class SurahsRVAdapter(private var onItemClick : (String , Int) -> Unit) : RecyclerView.Adapter<SurahsRVAdapter.ViewHolder>(){

    private val differUtil = object : DiffUtil.ItemCallback<Surah>(){
        override fun areItemsTheSame(oldItem: Surah, newItem: Surah): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: Surah, newItem: Surah) = oldItem == newItem
    }

    val listDiffer = AsyncListDiffer(this , differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SurahItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surah = listDiffer.currentList[position]
        holder.bind(surah)
        holder.setOnItemClickListener()
    }

    override fun getItemCount() = listDiffer.currentList.size

    inner class ViewHolder(private val mBinding : SurahItemBinding) :
        RecyclerView.ViewHolder(mBinding.root){
            fun bind(surah: Surah){
                mBinding.surahNameTv.text = surah.name
                mBinding.versesNoTv.text = surah.versesNumber.toString()
            }

            fun setOnItemClickListener(){
                mBinding.root.setOnClickListener {
                    val title =  "${mBinding.surahNameTv.text}"
                    onItemClick(title,adapterPosition)
                }
            }
    }
}