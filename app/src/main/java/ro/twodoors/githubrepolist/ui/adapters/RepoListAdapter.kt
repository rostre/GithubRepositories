package ro.twodoors.githubrepolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*
import ro.twodoors.githubrepolist.R
import ro.twodoors.githubrepolist.data.Item
import ro.twodoors.githubrepolist.data.RepoResult

class RepoListAdapter(private val repoList: RepoResult) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindRepo(repoList.items[position])
  }

  override fun getItemCount(): Int = repoList.items.size

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindRepo(repo: Item) {
      itemView.username.text = repo.owner.login.orEmpty()
      itemView.repoName.text = repo.fullName.orEmpty()
      itemView.repoDescription.text = repo.description.orEmpty()
      Picasso.get()
              .load(repo.owner.avatar_url)
              .placeholder(R.drawable.ic_launcher_background)
              .into(itemView.icon)
    }
  }
}