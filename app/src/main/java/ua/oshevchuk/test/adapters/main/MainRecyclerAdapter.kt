package ua.oshevchuk.test.adapters.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.oshevchuk.test.databinding.UserItemBinding
import ua.oshevchuk.test.models.users.UserModel

/**
 * @author shevsan on 28.07.2022
 */
class MainRecyclerAdapter(private val clickListener: (UserModel) -> Unit) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {
    class MainViewHolder(val binding: UserItemBinding, val clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }



   private var users  = ArrayList<UserModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val vb = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(vb) {
            clickListener(users[it])
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = users[position]
        holder.binding.userName.text = user.login
        Glide.with(holder.itemView).load(user.avatar_url).into(holder.binding.userIcon)
    }

    override fun getItemCount(): Int {
        return users.size
    }
    fun setData(list:List<UserModel>){
        users = list as ArrayList<UserModel>
        this.notifyDataSetChanged()
    }
}