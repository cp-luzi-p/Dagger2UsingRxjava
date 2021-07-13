package com.example.dagger2sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger2sample.databinding.ListUserItemBinding
import com.example.dagger2sample.model.User

class UserAdapter (fragment: Fragment) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val listener: ItemClickListener

    init {
        this.listener = fragment as ItemClickListener
    }

    var binding: ListUserItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ListUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userList = users[position]
        holder.bind(userList)

        binding!!.cardLayout.setOnClickListener {
            listener.displayItem(userList.getId().toString())
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(
        private var binding: ListUserItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userId.text = user.getId().toString()
            binding.userName.text = user.getName()
            binding.userEmail.text = user.getEmail()
        }
    }

    interface ItemClickListener {
        fun displayItem(userId: String)
    }
}