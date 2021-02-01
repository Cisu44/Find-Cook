package com.example.findcook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.findcook.favourites.FavouritesFragment
import com.example.findcook.forLater.ForLaterFragment
import com.example.findcook.home.HomeFragment
import com.example.findcook.recipeDetails.RecipeDetailsFragment
import com.google.firebase.auth.FirebaseAuth


class MainMenuAcitivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu_acitivity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.favouritesFragment, R.id.forLaterFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener{ menuItem ->
            when(menuItem.itemId){
                R.id.nav_favourites ->{

                    val favouritesFragment = FavouritesFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, favouritesFragment)
                        .commit()

                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_forLater ->{

                    val forLaterFragment = ForLaterFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, forLaterFragment)
                        .commit()

                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_home->{

                    val mIntent = Intent(applicationContext, MainMenuAcitivity::class.java)
                    startActivity(mIntent)

                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false

            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
       // menuInflater.inflate(R.menu.main_menu_acitivity, menu)
        return true

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout ->{
                fbAuth.signOut()
                this.finish()
            }
        }
        return  false
    }


}