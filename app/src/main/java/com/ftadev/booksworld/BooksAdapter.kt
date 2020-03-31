package com.ftadev.booksworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: ArrayList<BookModel> = ArrayList()

    fun setUsers(users: List<BookModel>) {

        userList.addAll(users)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_book, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = userList[position]

//        holder.userId.text = user.id.toString()
//        holder.name.text = user.title
//        holder.userName.text = user.username
//        holder.email.text = user.email
        Picasso.get().load(user.photo).into(holder.image);
    }

    class UserViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

//        val userId = parent.findViewById<TextView>(R.id.userId)
//        val name = parent.findViewById<TextView>(R.id.name)
//        val userName = parent.findViewById<TextView>(R.id.userName)
//        val email = parent.findViewById<TextView>(R.id.email)
        val image = parent.findViewById<ImageView>(R.id.image)

    }

}