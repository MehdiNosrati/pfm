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

import java.util.ArrayList;
import java.util.Date;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.FragmentHomeBinding;
import io.mns.mpfm.db.entities.TransactionEntity;
import io.mns.mpfm.model.Transaction;
import io.mns.mpfm.ui.adapters.TransactionAdapter;
import io.mns.mpfm.viewmodels.HomeViewmodel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TransactionClickCallback {

    private FragmentHomeBinding binding;
    private HomeViewmodel viewmodel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(HomeViewmodel.class);
        binding.setViewmodel(viewmodel);
        TransactionAdapter adapter = new TransactionAdapter(this);
        binding.transactionList.setAdapter(adapter);
    }

    @Override
    public void onClick() {

    }
}
