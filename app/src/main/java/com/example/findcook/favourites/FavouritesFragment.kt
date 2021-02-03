package com.example.findcook.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.BaseFragment
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeAdapter
import com.example.findcook.home.HomeViewModel
import com.example.findcook.recipeDetails.RecipeDetailsFragment
import com.google.android.material.snackbar.Snackbar

class FavouritesFragment : BaseFragment(), RecipeAdapter.OnRecipeClick{

    private val favouritesViewModel by viewModels<FavouritesViewModel>()
    private val adapter = RecipeAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val title = context?.resources?.getString(R.string.favourites)
        activity?.title = title
        return inflater.inflate(R.layout.favourites_fragment, container, false)
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
        favouritesViewModel.removeRecipe(recipe)
        adapter.removeRecipe(recipe,position)

        val snackbarText = getString(R.string.recipe_remove, recipe.name)
        Snackbar.make(requireView(),snackbarText, Snackbar.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favRV = view.findViewById<RecyclerView>(R.id.favouritesRecyclerView)
        favRV?.layoutManager = LinearLayoutManager(requireContext())
        favRV?.adapter = adapter

        val itemDecoration = DividerItemDecoration(favRV.context,
            (favRV.layoutManager as LinearLayoutManager).orientation)
        favRV.addItemDecoration(itemDecoration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favouritesViewModel.favRecipes.observe(viewLifecycleOwner, {list ->
            list?.let {
                adapter.setRecipes(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }


}


