package com.example.dagger2sample.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2sample.R
import com.example.dagger2sample.adapter.PostAdapter
import com.example.dagger2sample.databinding.FragmentPostsBinding
import com.example.dagger2sample.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding
    private lateinit var viewModel: PostsViewModel

    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(requireActivity()))
            .get(PostsViewModel::class.java)

        val userId = arguments?.getInt(USER_KEY)
        viewModel.loadPosts(userId!!)

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.recyclerViewPostsList.adapter = PostAdapter().apply {
            viewModel.posts.observe(viewLifecycleOwner, Observer {
                this.albums = it
            })
        }
        binding.recyclerViewPostsList.setHasFixedSize(true)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.onErrorClick)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    companion object {
        var USER_KEY: String = "user_id"

        fun newInstance(id: Int): PostsFragment {
            val postsFragment = PostsFragment()
            val bundle = Bundle()
            bundle.putInt(USER_KEY, id)
            postsFragment.arguments = bundle
            return postsFragment
        }
    }

}