package com.shift.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shift.R
import com.shift.adapter.UserAdapter
import com.shift.dao.UserDao
import com.shift.db.Appdb
import com.shift.entity.UserEntity
import com.shift.model.User
import com.shift.model.UserResponse
import com.shift.model.UserService
import com.shift.repository.RandomUserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CardListsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userDao: UserDao
    private val userService = UserService()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_card_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.main)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = Appdb.getInstance(requireContext())
        userDao = db.userDao()

        view.findViewById<ImageButton>(R.id.btn_refresh).setOnClickListener{
            refreshUsers()
        }


        lifecycleScope.launch {
            val users: List<UserEntity> = withContext(Dispatchers.IO) { userDao.getAllUsers() }
            if (users.size < 20) {
                fetchUsersFromApiAndSave()
            } else {
                val usersToDisplay: List<User> = users.map { it.toUser() }
                setupRecyclerView(usersToDisplay)
            }
        }




    }

    private fun refreshUsers() {
        lifecycleScope.launch(Dispatchers.IO) {
            userDao.deleteAllUsers() // Очистка базы данных
            fetchUsersFromApiAndSave() // Загрузка и сохранение новых пользователей
        }
    }


    private fun setupRecyclerView(users : List<User>){
        recyclerView.adapter = UserAdapter(users){ user ->
            val fragment = FullInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("user", user)
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun fetchUsersFromApiAndSave(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val randomUserApi = retrofit.create(RandomUserApi::class.java)

        randomUserApi.getRandomUser(20).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val users = response.body()?.results?: emptyList()

                    lifecycleScope.launch(Dispatchers.IO){
                        userDao.saveUsers(users.map { UserEntity.fromUser(it) })
                        val newUsersFromDb = userDao.getAllUsers()
                        val usersToDisplay = newUsersFromDb.map { it.toUser() }

                        withContext(Dispatchers.Main){
                            setupRecyclerView(usersToDisplay)
                        }
                    }

                }
                else{
                    InternetErrorToast()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                InternetErrorToast()
            }

        })
    }


    private fun InternetErrorToast() {
        activity?.runOnUiThread {
            Toast.makeText(context, "Проблема с интернет-соединением.", Toast.LENGTH_LONG).show()
        }
    }

}