package ua.oshevchuk.test.ui.detailsGitRepos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import ua.oshevchuk.test.R
import ua.oshevchuk.test.adapters.details.DetailsRecyclerAdapter
import ua.oshevchuk.test.core.baseFragment.BaseFragment
import ua.oshevchuk.test.databinding.FragmentDetailsBinding
import ua.oshevchuk.test.models.users.UserModel
import ua.oshevchuk.test.utils.bundleKey

/**
 * @author shevsan on 28.07.2022
 */
@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private lateinit var currentUser: UserModel
    private lateinit var viewModel: DetailsViewModel
    private lateinit var adapter: DetailsRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser = arguments?.getSerializable(bundleKey) as UserModel
        binding.userName.text = currentUser.login
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        initViewModel()
        initAdapter()
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initViewModel() {
        viewModel.getReposFromDB(currentUser.login)
        viewModel.getReposFromApi(currentUser.login)
        viewModel.getRepos().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun initAdapter() {
        adapter = DetailsRecyclerAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.html_url))
            startActivity(intent)

        }
        binding.repoRecycler.adapter = adapter
    }
}