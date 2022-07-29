package ua.oshevchuk.test.adapters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.oshevchuk.test.databinding.RepositoriesItemBinding
import ua.oshevchuk.test.models.details.RepositoryModel

/**
 * @author shevsan on 29.07.2022
 */
class DetailsRecyclerAdapter: RecyclerView.Adapter<DetailsRecyclerAdapter.DetailsViewHolder>() {
    private var repos = ArrayList<RepositoryModel>()
    class DetailsViewHolder(val binding:RepositoriesItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val vb = RepositoriesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailsViewHolder(vb)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val repo = repos[position]
        holder.binding.apply {
            repoName.text = repo.name
            starred.text = repo.stargazers_count.toString()
            langName.text = repo.language
        }
    }

    override fun getItemCount(): Int {
        return repos.size
    }
    fun setData(list:List<RepositoryModel>){
        repos = list as ArrayList<RepositoryModel>
        this.notifyDataSetChanged()
    }
}