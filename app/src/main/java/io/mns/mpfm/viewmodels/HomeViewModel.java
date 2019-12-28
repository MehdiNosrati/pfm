package io.mns.mpfm.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.List;

import io.mns.mpfm.DataRepository;
import io.mns.mpfm.PfmApplication;
import io.mns.mpfm.R;
import io.mns.mpfm.db.entities.Transaction;

public class HomeViewModel extends ViewModel {
    private DataRepository dataRepository;

    public HomeViewModel(@NonNull Application application) {
        super();
        dataRepository = ((PfmApplication) application).getRepository();
    }

    public HomeViewModel(){}

    public void navigateToTransaction(View v) {
        Navigation.findNavController(v).navigate(R.id.home_to_add_transaction);
    }

    public LiveData<List<Transaction>> loadTransactions() {
        return dataRepository.getTransactions();
    }
}
