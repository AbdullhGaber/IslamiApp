package com.example.islamiapp.ui.fragments.home.ahadeeth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.islamiapp.data.models.Hadeeth
import com.example.islamiapp.databinding.HadeethItemBinding

class AhadeethRVAdapter(private var onItemClick : (Hadeeth) -> Unit) : RecyclerView.Adapter<AhadeethRVAdapter.ViewHolder>(){

    private val differUtil = object : DiffUtil.ItemCallback<Hadeeth>(){
        override fun areItemsTheSame(oldItem: Hadeeth, newItem: Hadeeth): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Hadeeth, newItem: Hadeeth) = oldItem == newItem
    }

    val listDiffer = AsyncListDiffer(this , differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HadeethItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surah = listDiffer.currentList[position]
        holder.bind(surah)
        holder.setOnItemClickListener()
    }

    override fun getItemCount() = listDiffer.currentList.size

    inner class ViewHolder(private val mBinding : HadeethItemBinding) :
        RecyclerView.ViewHolder(mBinding.root){
            fun bind(hadeeth: Hadeeth){
                mBinding.haddethNameTv.text = hadeeth.title
            }

            fun setOnItemClickListener(){
                mBinding.root.setOnClickListener {
                    onItemClick(listDiffer.currentList[adapterPosition])
                }
            }
    }
}