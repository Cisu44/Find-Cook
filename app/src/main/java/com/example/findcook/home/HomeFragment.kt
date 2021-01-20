package com.example.findcook.home

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.MainMenuAcitivity
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeSmall
import com.example.findcook.data.RecipeSmallAdapter
import com.example.findcook.recipeDetails.RecipeDetailsFragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class  HomeFragment : Fragment(), RecipeSmallAdapter.OnRecipeClick {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter = RecipeSmallAdapter(this)


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

    override fun onRecipeClick(recipeSmall: RecipeSmall, position: Int) {

        val detailFragment = RecipeDetailsFragment(recipeSmall)
        val backStateName = this.javaClass.name
        val transaction = activity?.supportFragmentManager

        transaction?.beginTransaction()?.replace(R.id.nav_host_fragment, detailFragment)
           ?.addToBackStack(backStateName)
           ?.commit()


    }


}