package com.example.simplified.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simplified.R
import com.example.simplified.common.constant.Config
import com.example.simplified.common.di.ComponentsProvider
import com.example.simplified.common.network.Status
import com.example.simplified.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_repo_detail.*
import timber.log.Timber
import javax.inject.Inject

class RepoDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }
    private var comments: String? = ""
    private var followers: String? = ""
    @Inject
    lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        ComponentsProvider.getComponent().inject(this)
        title = "Repo Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent?.extras?.getString(Config.ID)
        comments = intent?.extras?.getString(Config.COMMENTS)
        followers = intent?.extras?.getString(Config.FOLLOWERS)

        getFollowers(followers)
        fetchRepoDetail(id ?: "")
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }

    private fun getFollowers(followers: String?) {
        followers?.let {
            homeViewModel.getRepoFollowers(it).observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { followersList ->
                            Timber.d("followers", followersList)
                            tv_followers.text = followersList.size.toString()
                        }

                    }
                    Status.ERROR -> {
                        Timber.d("repo detail error", it.error)

                    }
                    Status.LOADING -> {

                    }
                }
            })


            homeViewModel.getRepoFollowing(it).observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { followersList ->
                            Timber.d("followers", followersList)
                            tv_following.text = followersList.size.toString()
                        }

                    }
                    Status.ERROR -> {
                        Timber.d("repo detail error", it.error)

                    }
                    Status.LOADING -> {

                    }
                }
            })
        }


    }

    private fun fetchRepoDetail(id: String) {
        homeViewModel.getRepoDetails(id).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { detail ->

                        Timber.d("repo detail", it.data.toString())
                        ll_detail.visibility = View.VISIBLE
                        picasso.load(detail.owner?.avatarUrl).fit().into(iv_avatar)
                        tv_name.text = detail.name
                        tv_full_name.text = detail.fullName

                        tv_desc.text = detail.description

                        tv_repo_url.text = detail.owner?.reposUrl
                        tv_node_id.text = detail.nodeId
                        tv_user_comments.text = comments
                        tv_user_type.text = detail.owner?.type

                        pb_loading_detail.visibility = View.GONE
                    }

                }
                Status.ERROR -> {
                    Timber.d("repo detail error", it.error)
                    pb_loading_detail.visibility = View.GONE
                }
                Status.LOADING -> {
                    pb_loading_detail.visibility = View.VISIBLE
                }
            }
        })
    }
}
