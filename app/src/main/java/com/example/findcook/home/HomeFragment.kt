package com.example.findcook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findcook.R
import com.example.findcook.data.Recipe
import com.example.findcook.data.RecipeSmall
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class  HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val firebase = FirebaseFirestore.getInstance()
    private var adapter: RecipeFirestoreRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val homeRecyclerView = view?.findViewById<RecyclerView>(R.id.homeRecycler)
        homeRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        val query = firebase.collection("Recipes").orderBy("name", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<RecipeSmall>().setQuery(query, RecipeSmall::class.java).build()

        adapter = RecipeFirestoreRecyclerAdapter(options)
        homeRecyclerView?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()

        if(adapter != null)
            adapter!!.stopListening()
    }

    private inner class RecipeViewHolder(private val view: View)
        :RecyclerView.ViewHolder(view){

            internal fun setRecipe(name: String, complexity: String, category: String){
                val nameTV = view.findViewById<TextView>(R.id.recipe_name_textView)
                val complexityTV = view.findViewById<TextView>(R.id.recipe_complexity_textView)
                val categoryTV = view.findViewById<TextView>(R.id.recipe_main_category_textView)

                nameTV.text = name
                complexityTV.text = complexity
                categoryTV.text = category
            }
        }

    private inner class RecipeFirestoreRecyclerAdapter constructor(options: FirestoreRecyclerOptions<RecipeSmall>)
        :FirestoreRecyclerAdapter<RecipeSmall, RecipeViewHolder>(options){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_single_row, parent,false)
            return RecipeViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int, model: RecipeSmall) {
            holder.setRecipe(model.name, model.complexityLevel, model.category)
        }


    }



}