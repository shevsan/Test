package ua.oshevchuk.test.ui.detailsGitRepos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ua.oshevchuk.test.R
import ua.oshevchuk.test.core.BaseFragment
import ua.oshevchuk.test.databinding.FragmentDetailsBinding
import ua.oshevchuk.test.models.users.UserModel

/**
 * @author shevsan on 28.07.2022
 */
@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private lateinit var currentUser:UserModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}