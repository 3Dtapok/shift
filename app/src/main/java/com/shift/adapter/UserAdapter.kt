package com.shift.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.shift.R
import com.shift.fragments.CardListsFragment
import com.shift.fragments.FullInfoFragment
import com.shift.model.User
import com.shift.model.UserService
import com.squareup.picasso.Picasso

interface OnUserClickListener {
    fun onUserClicked(user: User)
}

class UserAdapter(private val userService: List<User>, private val listener: (User) -> Unit): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val avatar: ImageView = view.findViewById(R.id.avatar)
        val name: TextView = view.findViewById(R.id.name)
        val address: TextView = view.findViewById(R.id.address)
        val phoneNumber: TextView = view.findViewById(R.id.phoneNumber)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //val user = userService.getUsers()[position]
        val user = userService[position]
        holder.name.text = "${user.name.first} ${user.name.last}"
        holder.address.text = "${user.location.postcode} ${user.location.street.name}"
        holder.phoneNumber.text = "${user.phone}"

        Picasso.get()
            .load(user.picture.medium)
            .into(holder.avatar)

        holder.itemView.setOnClickListener{
            listener(user)

        }
    }

    override fun getItemCount() = userService.size
   // override fun getItemCount() = userService.getUsers().size



}