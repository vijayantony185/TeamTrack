package com.example.teamtrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtrack.R
import com.example.teamtrack.arch.Data
import com.example.teamtrack.databinding.AdapterUserDetailBinding

class UserPagingAdapter(val context: Context, val item : ItemListener) : PagingDataAdapter<Data, UserPagingAdapter.UserHolder>(
    USER_COMPARATOR) {
    inner class UserHolder(private val binding: AdapterUserDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Data) {
            binding.apply {
                cvUserDetails.setOnClickListener {
                    item.item(user)
                }
                tvUserName.text = "${user.firstName} ${user.lastName}"
                tvUserEmail.text = user.email
                tvUserCountry.text = user.country
                Glide.with(context).load(user.avatar).placeholder(R.mipmap.ic_launcher).into(ivUserImage)
            }
        }

    }


    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = getItem(position)
        user?.let { data ->
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(AdapterUserDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}

interface ItemListener{
    fun item(data: Data)
}