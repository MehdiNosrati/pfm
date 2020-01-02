package io.mns.mpfm.ui.fragments;


import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
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
import io.mns.mpfm.db.entities.Transaction;
import io.mns.mpfm.viewmodels.TransactionViewModel;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;
    private TransactionViewModel viewModel;
    private int transactionType = -1;
    private int transactionIdToEdit = -1;
    private Transaction previousTransaction = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        setupViewModel();
        setupListeners();
        checkArguments();
    }

    private void checkArguments() {
        if (getArguments() != null) {
            transactionIdToEdit = TransactionFragmentArgs.fromBundle(getArguments()).getTransactionId();
            if (transactionIdToEdit != -1) {
                changeToEditMode();
            }
        }
    }

    private void changeToEditMode() {
        viewModel.getTransactionById(transactionIdToEdit).observe(this, transaction -> {
            if (transaction != null) {
                binding.remove.setVisibility(View.VISIBLE);
                previousTransaction = transaction;
                binding.title.setText(transaction.getTitle());
                binding.value.setText(String.valueOf(Math.abs(transaction.getValue())));
                transactionType = transaction.getType() == Transaction.TransactionType.INCOME ? 1 : -1;
                binding.typeSelector.setPosition(transactionType == 1 ? 0 : 1);
                if (transaction.getType() == Transaction.TransactionType.INCOME)
                    ((TransitionDrawable) binding.parent.getBackground()).reverseTransition(300);
            }
        });
    }

    private void setupListeners() {
        binding.submit.setOnClickListener(v -> {
            String title = binding.title.getText().toString();
            String value = binding.value.getText().toString();
            if (!title.isEmpty() && !value.isEmpty() && Long.valueOf(value) != 0) {
                submitTransaction(v, title, value);
                goBack(v);
            } else {
                Toast.makeText(
                        getContext(), R.string.invalid_transaction_error, Toast.LENGTH_LONG
                ).show();
            }
        });

        binding.typeSelector
                .setOnClickedButtonListener(position -> {
                    if (position == 0) {
                        transactionType = 1;
                        binding.title.setHint(R.string.income_title_hint);
                        ((TransitionDrawable) binding.parent.getBackground()).startTransition(300);
                    } else {
                        transactionType = -1;
                        binding.title.setHint(R.string.expense_title_hint);
                        ((TransitionDrawable) binding.parent.getBackground()).reverseTransition(300);
                    }
                });

        binding.remove.setOnClickListener(v -> {
            if (v.getVisibility() == View.VISIBLE) {
                viewModel.removeTransaction(previousTransaction);
                viewModel.updateBalance(v.getContext().getApplicationContext(), 0, previousTransaction);
                goBack(v);
            }
        });
    }

    private void submitTransaction(View v, String title, String value) {
        if (previousTransaction != null) {
            viewModel.updateBalance(v.getContext().getApplicationContext(),
                    Long.valueOf(value) * transactionType,
                    previousTransaction);
            viewModel.submit(previousTransaction, title, Long.valueOf(value) * transactionType);
        } else {
            viewModel.updateBalance(v.getContext().getApplicationContext(),
                    Long.valueOf(value) * transactionType);
            viewModel.submit(title, Long.valueOf(value) * transactionType);
        }
        hideKeyboard();
    }

    private void goBack(View v) {
        Navigation.findNavController(v).navigate(R.id.add_transaction_to_home);
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
