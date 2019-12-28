package io.mns.mpfm.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.FragmentTransactionBinding;
import io.mns.mpfm.viewmodels.TransactionViewModel;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;
    private TransactionViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        binding.submit.setOnClickListener(v -> {
            viewModel.submit(binding.title.getText().toString(),
                    Long.valueOf(binding.value.getText().toString()));
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
