package com.example.simplified.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.simplified.R
import com.example.simplified.common.di.ComponentsProvider
import com.example.simplified.common.network.Status
import com.example.simplified.common.utils.AppUtils
import com.example.simplified.models.Response
import com.example.simplified.ui.RepoAdapter
import com.example.simplified.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import javax.inject.Inject

class RepoListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var picasso: Picasso

    lateinit var adapter: RepoAdapter

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this.requireActivity(), viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ComponentsProvider.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        fetchRepoList()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //** Set the Layout Manager of the View
        setLayoutManager()
    }

    private fun setLayoutManager() {
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.requireContext())
        mLayoutManager.orientation = RecyclerView.VERTICAL
        recycler_view.layoutManager = mLayoutManager
        recycler_view.setHasFixedSize(true)
    }

    private fun fetchRepoList() {
        if (AppUtils.isConnectingToInternet(this.requireActivity())){
            homeViewModel.getRepoList().observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data ->
                            pb_loading.visibility = View.GONE
                            if (data.isNotEmpty()) {
                                Timber.d("success$data")
                                recycler_view.visibility = View.VISIBLE
                                tv_no_data.visibility = View.GONE
                                setAdapter(data)
                            } else {
                                tv_no_data.visibility = View.VISIBLE
                                recycler_view.visibility = View.GONE
                            }

                        }
                    }
                    Status.ERROR -> {
                        pb_loading.visibility = View.GONE
                        Timber.d("Some thing went wrong"+ it.error)
                    }
                    Status.LOADING -> {
                        pb_loading.visibility = View.VISIBLE
                    }
                }
            })
        } else {
            Toast.makeText(getActivity(), "Please check your internet connection",
                Toast.LENGTH_LONG).show();
        }

    }

    private fun setAdapter(data: List<Response>) {
        adapter = RepoAdapter(data, picasso)
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
    }
}