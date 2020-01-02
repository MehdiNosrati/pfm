package io.mns.mpfm.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import io.mns.mpfm.R
import io.mns.mpfm.databinding.FragmentFilterBinding
import io.mns.mpfm.db.entities.Transaction
import io.mns.mpfm.ui.adapters.TransactionAdapter
import io.mns.mpfm.ui.callbacks.TransactionClickCallback
import io.mns.mpfm.viewmodels.FilterType
import io.mns.mpfm.viewmodels.FilterViewModel

class FilterFragment : Fragment(), TransactionClickCallback {

    private lateinit var binding: FragmentFilterBinding
    private lateinit var viewModel: FilterViewModel
    private lateinit var filterType: FilterType
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        setupViewModel()
        checkArguments()
    }

    private fun checkArguments() {
        if (view != null) {
            val filterTypeArg = FilterFragmentArgs.fromBundle(arguments!!).filterType
            if (filterTypeArg != -1) {
                when (filterTypeArg) {
                    0 -> filterType = FilterType.INCOME
                    1 -> filterType = FilterType.EXPENSE
                }
            }
            setupList()
            startDataObservation()
        }
    }

    private fun startDataObservation() {
        viewModel.filter(filterType).observe(this, Observer {
            if (it.isEmpty()) {
                binding.empty = true
            } else {
                adapter.setData(it)
            }
        })
    }

    private fun setupList() {
        adapter = TransactionAdapter(this)
        binding.list.adapter = adapter
        binding.filterTitle.text = filterType.name
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)
    }

    override fun onClick(transaction: Transaction?) {
        if (view != null) {
            val action: NavDirections = FilterFragmentDirections.filterToAddTransaction().setTransactionId(transaction!!.id)
            Navigation.findNavController(view!!).navigate(action)
        }
    }


}
