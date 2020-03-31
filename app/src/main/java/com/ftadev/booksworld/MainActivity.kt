package com.ftadev.booksworld

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*


class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val firstFragment = AllBooksFragment()
    private val secondFragment = CategoryBooksFragment()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For gradient status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        setContentView(R.layout.activity_main)

//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.book_list_fragment, firstFragment)
//        fragmentTransaction.commit()

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        userAdapter = UserAdapter()

        //setting layout manager to recycler view and adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = userAdapter

        //before calling api register live data observer
        registerObservers()

        //calling user list api
        mainViewModel.getUsers()


        chips_group.setOnCheckedChangeListener { chipGroup, i ->
            val chip: Chip? = chipGroup.findViewById(i)
            if (chip != null) {
                if (chip.id == R.id.all_chip)
                    allBookClick()
                else
                    categoryBookClick()
            }
        }

    }

    private fun registerObservers() {

        mainViewModel.usersSuccessLiveData.observe(this, Observer { userList ->

            //if it is not null then we will display all users
            userList?.let {
                userAdapter.setUsers(it)
            }
        })

        mainViewModel.usersFailureLiveData.observe(this, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun allBookClick() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.book_list_fragment, firstFragment)
        fragmentTransaction.commit()
    }

    private fun categoryBookClick() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.book_list_fragment, secondFragment)
        fragmentTransaction.commit()
    }

}
