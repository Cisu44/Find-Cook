package com.example.findcook.forLater

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeAdapter
import com.example.findcook.home.HomeViewModel
import com.example.findcook.recipeDetails.RecipeDetailsFragment
import com.google.android.material.snackbar.Snackbar

class ForLaterFragment : Fragment(), RecipeAdapter.OnRecipeClick {

    private val forLaterViewModel by viewModels<ForLaterViewModel>()
    private val adapter = RecipeAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.for_later_fragment, container, false)
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
        forLaterViewModel.removeRecipe(recipe)
        adapter.removeRecipe(recipe,position)

        val snackbarText = getString(R.string.recipe_remove, recipe.name)
        Snackbar.make(requireView(),snackbarText, Snackbar.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val forLaterRV = view.findViewById<RecyclerView>(R.id.forLaterRecyclerView)
        forLaterRV.layoutManager = LinearLayoutManager(requireContext())
        forLaterRV.adapter=adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forLaterViewModel.forLaterRecipes.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.setRecipes(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

}