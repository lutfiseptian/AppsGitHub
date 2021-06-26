package com.example.appstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.appstest.Fragment.FragmentIssue
import com.example.appstest.Fragment.FragmentRepositori
import com.example.appstest.Fragment.FragmentUser
import com.example.appstest.Models.ResponseModelUser

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    val arrayList = ArrayList<items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentUser = FragmentUser()
        val fragmentRepositori = FragmentRepositori()
        val fragmentIssue = FragmentIssue()

        makeCurrentFragment(fragmentUser)
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.userFragment -> makeCurrentFragment(fragmentUser)
                R.id.repositoriFragment -> makeCurrentFragment(fragmentRepositori)
                R.id.issueFragment -> makeCurrentFragment(fragmentIssue)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper , fragment)
            commit()
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.bottom_menu, menu)
        val menuItem = menu!!.findItem(R.id.searchUser)
        val searchview = menuItem.actionView as SearchView
        searchview.maxWidth = Int.MAX_VALUE
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}