package tj.task.graphqlleadsapp.presentation.lead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.task.graphqlleadsapp.R
import tj.task.graphqlleadsapp.databinding.FragmentDetailedLeadBinding
import tj.task.graphqlleadsapp.domain.model.Intention
import tj.task.graphqlleadsapp.domain.model.LeadIntentionType
import tj.task.graphqlleadsapp.domain.model.LeadStatusType
import tj.task.graphqlleadsapp.domain.model.Status
import tj.task.graphqlleadsapp.extension.serializable
import tj.task.graphqlleadsapp.presentation.create_lead.LeadIntentionTypesDialog

@AndroidEntryPoint
class DetailedLeadFragment : Fragment() {

    private var _binding: FragmentDetailedLeadBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel: LeadViewModel by viewModels()

    private var leadId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leadId = it.getInt(ARG_LEAD_ID, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedLeadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchDetailedLead(leadId)
        initView()
        initObservers()

        setFragmentResultListener(LeadStatusTypesDialog.REQUEST_KEY) { requestKey, bundle ->
            val itemStatusType =
                bundle.serializable(LeadStatusTypesDialog.ARG_LEAD_STATUS_TYPE_ITEM) as? LeadStatusType

            binding.viewTopInfoLeadProfile.prepareStatusType(
                Status(
                    backgroundColor = itemStatusType?.backgroundColor ?: "",
                    color = itemStatusType?.color ?: "",
                    id = itemStatusType?.id ?: 0,
                    step = itemStatusType?.step ?: 0,
                    stepsCount = itemStatusType?.stepsCount ?: 0,
                    title = itemStatusType?.title ?: ""
                )
            )
        }

        setFragmentResultListener(LeadIntentionTypesDialog.REQUEST_KEY) { requestKey, bundle ->
            val itemIntentionType =
                bundle.serializable(LeadIntentionTypesDialog.ARG_LEAD_INTENTION_TYPE) as? LeadIntentionType
            binding.viewGeneralInfoLeadProfile.setIntentionType(
                Intention(id = itemIntentionType?.id ?: 0, title = itemIntentionType?.title ?: "")
            )
        }
    }

    private fun initView() = with(binding) {
        toolbarDetailedLead.viewCreateNewLead.isVisible = false
        toolbarDetailedLead.toolbarMain.setNavigationIcon(R.drawable.ic_back)
        toolbarDetailedLead.toolbarMain.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.viewTopInfoLeadProfile.onLeadStatusItemClickListener =
            View.OnClickListener {
                LeadStatusTypesDialog().show(
                    parentFragmentManager,
                    LeadStatusTypesDialog.TAG_LEAD_STATUS_TYPE_DIALOG
                )
            }
        binding.viewGeneralInfoLeadProfile.onIntentionInputClickListener = View.OnClickListener {
            LeadIntentionTypesDialog().show(
                parentFragmentManager,
                LeadIntentionTypesDialog.TAG_LEAD_INTENTION_TYPE_DIALOG
            )
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.leadState.collectLatest {
                    if (it.isLoading) {
                        binding.pbLoading.isVisible = true
                        binding.parentScrollview.isVisible = false
                    } else {
                        binding.pbLoading.isVisible = false
                        binding.parentScrollview.isVisible = true
                        it.detailedLead?.let { aLead ->
                            binding.viewGeneralInfoLeadProfile.setItem(aLead)
                            binding.viewTopInfoLeadProfile.setItem(aLead)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_LEAD_ID = "arg_lead_id"
    }
}