package com.example.simplified.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.example.simplified.R
import com.example.simplified.common.constant.Config
import com.example.simplified.models.Response
import com.example.simplified.ui.home.RepoDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo_list.view.*


class RepoAdapter(
    private var itemList: List<Response>,
    private var picasso: Picasso
) :
    RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_repo_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flag = position == itemCount - 1
        holder.bindData(getItem(position))
    }

    // Return the size of your dataSet (invoked by the layout manager)
    override fun getItemCount(): Int = itemList.size

    fun getItem(position: Int): Response {
        return itemList[position]
    }

    inner class ViewHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        fun bindData(item: Response) {
            itemView.tv_name.text = item.name
            itemView.tv_desc.text = item.description
            itemView.tv_url.text = item.url
            picasso.load(item.owner?.avatarUrl)?.fit()?.into(itemView.iv_avatar)

            itemView.setOnClickListener {
                val comments = itemView.et_comments.text.toString()
                val intent = Intent(itemView.context, RepoDetailActivity::class.java)
                intent.putExtra(Config.FOLLOWERS, item.owner?.login)
                intent.putExtra(Config.COMMENTS, comments)
                intent.putExtra(Config.ID, item.id.toString())
                itemView.context.startActivity(intent)
            }

            itemView.et_comments.setOnEditorActionListener { v, actionId, event ->

                when(actionId){
                    EditorInfo.IME_ACTION_DONE->{
                        itemView.et_comments.clearFocus()
                    }
                    else->{
                        Log.w("TAG", "another action id ${actionId}")
                    }
                }

                false
            }

        }
    }


}