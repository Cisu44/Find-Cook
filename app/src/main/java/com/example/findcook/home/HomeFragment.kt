package com.example.findcook.home

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeAdapter
import com.example.findcook.recipeDetails.RecipeDetailsFragment
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList

class  HomeFragment : Fragment(), RecipeAdapter.OnRecipeClick, SearchView.OnQueryTextListener {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter = RecipeAdapter(this, this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeRecyclerView = view.findViewById<RecyclerView>(R.id.homeRecycler)
        homeRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        homeRecyclerView?.adapter=adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.recipes.observe(viewLifecycleOwner,{list ->
            adapter.setRecipes(list)
        })

    }

    override fun onRecipeClick(recipe: Recipe, position: Int) {

        val detailFragment = RecipeDetailsFragment(recipe)
        val backStateName = this.javaClass.name
        val transaction = activity?.supportFragmentManager

        transaction?.beginTransaction()?.replace(R.id.nav_host_fragment, detailFragment)
           ?.addToBackStack(backStateName)
           ?.commit()
    }

    override fun onRecipeLongClick(recipe: Recipe, position: Int) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if(!menu.hasVisibleItems()) {
            inflater.inflate(R.menu.main_menu_acitivity, menu)

            val searchItem = menu.findItem(R.id.mSearchView)
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(this)

            super.onCreateOptionsMenu(menu, inflater)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText==null||newText.trim().isEmpty())
        {
            homeViewModel.recipes.observe(viewLifecycleOwner,{list ->
                adapter.setRecipes(list)
            })
            return false
        }
        val mNewText=newText.trim().toLowerCase(Locale.ROOT)
        adapter.setRecipes(adapter.filter(mNewText))
        return true
    }




}