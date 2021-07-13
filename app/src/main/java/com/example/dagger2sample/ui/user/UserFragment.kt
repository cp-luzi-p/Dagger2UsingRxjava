package com.example.dagger2sample.ui.user


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.example.dagger2sample.databinding.FragmentUserBinding
import com.example.dagger2sample.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2sample.R
import com.example.dagger2sample.adapter.UserAdapter
import com.example.dagger2sample.ui.posts.PostsFragment


class UserFragment : Fragment(),UserAdapter.ItemClickListener {
    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel
    private var errorSnackbar: Snackbar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,ViewModelFactory(requireActivity())).get(UserViewModel::class.java)

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.recyclerViewUserList.adapter = UserAdapter(this).apply {
            viewModel.users.observe(viewLifecycleOwner, Observer {
                this.users = it
            })
        }
        binding.recyclerViewUserList.setHasFixedSize(true)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.onErrorClick)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    fun newInstance(): UserFragment {
        return UserFragment()
    }

    override fun displayItem(userId: String) {
        val postsFragment = PostsFragment.newInstance(userId.toInt())
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, postsFragment, "PostsFragment")
            .addToBackStack(null)
            .commit()
    }

}