package io.mns.mpfm.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.FragmentHomeBinding;
import io.mns.mpfm.ui.adapters.TransactionAdapter;
import io.mns.mpfm.ui.callbacks.TransactionClickCallback;
import io.mns.mpfm.viewmodels.HomeViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TransactionClickCallback {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private TransactionAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupListeners();
        setupTransactionList();
        startDataObservation();
    }

    private void startDataObservation() {
        viewModel.loadTransactions().observe(this, transactions ->
                adapter.setData(transactions));
    }

    private void setupTransactionList() {
        adapter = new TransactionAdapter(this);
        binding.transactionList.setAdapter(adapter);
    }

    private void setupListeners() {
        binding.newTransaction.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.home_to_add_transaction));
    }

    private void setupViewModel() {
        HomeViewModel.Factory factory = new HomeViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        binding.setViewmodel(viewModel);
    }

    @Override
    public void onClick() {

    }
}
