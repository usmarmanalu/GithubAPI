package com.example.appgithubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithubuser.databinding.FragmentListBinding
import com.example.appgithubuser.model.SearchViewModel

class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(ARG_QUERY)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        adapter = SearchAdapter()
        binding.rvGithub.adapter = adapter
        binding.rvGithub.layoutManager = LinearLayoutManager(requireContext())

        showLoading(true)

        query?.let {
            viewModel.searchUsers(it)
        } ?: run {
            Toast.makeText(requireContext(), "Query is empty or null", Toast.LENGTH_SHORT).show()
        }

        viewModel.userList.observe(viewLifecycleOwner) { searchResponseList ->
            showLoading(false)
            if (searchResponseList != null) {
                adapter.submitList(searchResponseList)
            } else {
                Toast.makeText(requireContext(), "Failed to load user data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val ARG_QUERY = "query"

        fun newInstance(query: String): SearchUserFragment {
            val fragment = SearchUserFragment()
            val args = Bundle().apply {
                putString(ARG_QUERY, query)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
