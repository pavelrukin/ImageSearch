package com.pavelrukin.imagesearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pavelrukin.imagesearch.R
import com.pavelrukin.imagesearch.model.ImageModel
import kotlinx.android.synthetic.main.image_item.view.*

class DataAdapter : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtilCallBack = object : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {

            return oldItem.searchText == newItem.searchText &&
                    oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

    fun submitList(list: List<ImageModel>) {
        differ.submitList(list)

    }

    val differ = AsyncListDiffer(this, diffUtilCallBack)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    private var onItemClickListener: ((ImageModel) -> Unit)? = null
    private var onLongItemClickListener: ((ImageModel) -> Unit)? = null

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemView.apply {
            tv_item_search_tex?.text = data.searchText

            Glide.with(this)
                .asGif()
                .load(data.url)
                .into(iv_item)

            setOnLongClickListener {
                onLongItemClickListener?.let { it(data) }
                true
            }

            setOnClickListener {
                onItemClickListener?.let { it(data) }
            }

        }
    }

    fun setOnItemClickListener(listener: (ImageModel) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnLongClickLstener(listener: (ImageModel) -> Unit) {
        onLongItemClickListener = listener
    }

}
