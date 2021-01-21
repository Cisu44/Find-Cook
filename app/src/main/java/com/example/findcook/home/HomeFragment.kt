package com.example.findcook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeAdapter
import com.example.findcook.recipeDetails.RecipeDetailsFragment

class  HomeFragment : Fragment(), RecipeAdapter.OnRecipeClick {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter = RecipeAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeRecyclerView = view.findViewById<RecyclerView>(R.id.homeRecycler)
        homeRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        homeRecyclerView.adapter=adapter
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


}