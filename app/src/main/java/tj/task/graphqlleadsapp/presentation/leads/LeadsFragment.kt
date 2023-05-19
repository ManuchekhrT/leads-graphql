package tj.task.graphqlleadsapp.presentation.leads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.R
import tj.task.graphqlleadsapp.databinding.FragmentLeadsBinding
import tj.task.graphqlleadsapp.domain.model.Lead
import tj.task.graphqlleadsapp.presentation.lead.DetailedLeadFragment
import tj.task.graphqlleadsapp.presentation.lead.LeadAdapter

@AndroidEntryPoint
class LeadsFragment : Fragment() {

    private var _binding: FragmentLeadsBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: LeadsViewModel by viewModels()
    private var leadAdapter: LeadAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLeads()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        toolbarLeads.viewCreateNewLead.isVisible = true
        toolbarLeads.toolbarMain.navigationIcon = null
        toolbarLeads.toolbarMain.setOnClickListener {
            findNavController().navigate(R.id.action_LeadsFragment_to_CreateNewLeadFragment)
        }

        leadAdapter = LeadAdapter()
        leadAdapter?.onItemClickListener = {
            onLeadItemClickListener(it)
        }
        rvLeads.adapter = leadAdapter
    }

    private fun onLeadItemClickListener(item: Lead) {
        findNavController().navigate(
            R.id.action_LeadsFragment_to_DetailedLeadFragment,
            bundleOf(DetailedLeadFragment.ARG_LEAD_ID to item.id)
        )
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.leadsState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.rvLeads.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.rvLeads.isVisible = true
                        leadAdapter?.submitList(it.leads)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}