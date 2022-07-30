package ua.oshevchuk.test.ui.mainUsers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ua.oshevchuk.test.R
import ua.oshevchuk.test.adapters.main.MainRecyclerAdapter
import ua.oshevchuk.test.core.BaseFragment
import ua.oshevchuk.test.databinding.FragmentMainBinding
import ua.oshevchuk.test.models.users.UserModel
import ua.oshevchuk.test.utils.bundleKey

/**
 * @author shevsan on 28.07.2022
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getUsersFromDB()
        viewModel.getUserList()
        initViewModel()
    }

    private fun initAdapter() {
        adapter = MainRecyclerAdapter(){
            onItemClicked(it)
        }
        binding.mainRecycler.adapter = adapter

    }

    private fun onItemClicked(user:UserModel) {
        val bundle= Bundle()
        bundle.putSerializable(bundleKey,user)
        requireActivity().findNavController(R.id.fragmentContainerView)
            .navigate(R.id.action_mainFragment_to_detailsFragment,bundle)
    }

    private fun initViewModel() {
        viewModel.getUsers().observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }
}