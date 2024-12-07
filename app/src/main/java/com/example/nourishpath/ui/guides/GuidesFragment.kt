package com.example.nourishpath.ui.guides

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nourishpath.data.api.nourishpath.response.Article
import com.example.nourishpath.databinding.FragmentGuidesBinding
import com.example.nourishpath.ui.detail.DetailActivity

class GuidesFragment : Fragment() {

    private var _binding: FragmentGuidesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GuidesViewModel>()
    private lateinit var guidesAdapter: GuidesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guidesAdapter = GuidesAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = guidesAdapter

        viewModel.listArticle.observe(viewLifecycleOwner) { article ->
            showArticles(article)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showArticles(article: List<Article>) {
        guidesAdapter.submitList(article)
        guidesAdapter.setOnItemClickCallback(object: GuidesAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val intentToDetail = Intent(requireContext(), DetailActivity::class.java)
                intentToDetail.putExtra("EXTRA_ARTICLE", data.id)
                startActivity(intentToDetail)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
