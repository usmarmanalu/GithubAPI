package com.example.appgithubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubuser.databinding.FragmentListBinding
import com.example.appgithubuser.model.ListViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var viewModel: ListViewModel
    private var username: String? = null
    private var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)
        type = arguments?.getString(ARG_TYPE)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        adapter = UserListAdapter()
        binding.rvGithub.adapter = adapter
        binding.rvGithub.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        binding.rvGithub.addItemDecoration(dividerItemDecoration)

        showLoading(true)

        if (!username.isNullOrEmpty() && !type.isNullOrEmpty()) {
            viewModel.fetchUserList(username!!, type!!)
        }

        viewModel.userList.observe(viewLifecycleOwner) { users ->
            showLoading(false)
            if (users != null) {
                adapter.submitList(users)
            } else {
                Toast.makeText(requireContext(), "OnFailure", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun updateSearchQuery(query: String) {
        viewModel.queryUserListByCriteria(query.takeIf { it.isNotBlank() } ?: "")
    }

    companion object {
        private const val ARG_USERNAME = "username"
        private const val ARG_TYPE = "type"

        fun newInstance(username: String, type: String): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}
