package com.example.navigationcomponentexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationcomponentexample.databinding.ItemUserBinding
import com.example.navigationcomponentexample.model.entities.UserEntity

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList = emptyList<UserEntity>()

    fun submitList(users: List<UserEntity>) {
        userList = users
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEntity) {
            binding.tvNombre.text = user.nombre
            binding.tvApellido.text = user.apellido
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size
}
