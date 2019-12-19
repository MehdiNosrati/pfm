package io.mns.mpfm.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import io.mns.mpfm.R;

public class HomeViewmodel extends AndroidViewModel {

    public HomeViewmodel(@NonNull Application application) {
        super(application);
    }

    public void navigateToTransaction(View v) {
        Navigation.findNavController(v).navigate(R.id.home_to_add_transaction);
    }
}
