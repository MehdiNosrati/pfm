package io.mns.mpfm.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

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
        setupViewModel();
        setupListeners();
    }

    private void setupListeners() {
        binding.submit.setOnClickListener(v -> {
            String title = binding.title.getText().toString();
            String value = binding.value.getText().toString();
            if (!title.isEmpty() && !value.isEmpty()) {
                viewModel.submit(title, Long.valueOf(value));
                hideKeyboard();
                Navigation.findNavController(v).navigate(R.id.add_transaction_to_home);
            } else {
                Toast.makeText(getContext(), R.string.invalid_transaction_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hideKeyboard() {
        if (getActivity() != null) {
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(binding.value.getWindowToken(), 0);
            }
        }
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
    }
}
