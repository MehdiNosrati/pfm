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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.FragmentTransactionBinding;
import io.mns.mpfm.ui.MainActivity;
import io.mns.mpfm.viewmodels.TransactionViewModel;

public class TransactionFragment extends Fragment {

    private FragmentTransactionBinding binding;
    private TransactionViewModel viewModel;
    private int transactionType = -1;

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
            if (!title.isEmpty() && !value.isEmpty() && Long.valueOf(value) != 0) {
                submitTransaction(v, title, value);
            } else {
                Toast.makeText(
                        getContext(), R.string.invalid_transaction_error, Toast.LENGTH_LONG
                ).show();
            }
        });

        binding.typeSelector
                .setOnClickedButtonListener(position -> {
                    if (position == 0)  {
                        transactionType = 1;
                        binding.title.setHint(R.string.income_title_hint);
                        ((TransitionDrawable) binding.parent.getBackground()).startTransition(300);
                    }
                    else  {
                        transactionType = -1;
                        binding.title.setHint(R.string.expense_title_hint);
                        ((TransitionDrawable) binding.parent.getBackground()).reverseTransition(300);
                    }
                });
    }

    private void submitTransaction(View v, String title, String value) {
        viewModel.submit(title, Long.valueOf(value) * transactionType);
        viewModel.updateBalance(v.getContext(), Long.valueOf(value) * transactionType);
        hideKeyboard();
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
