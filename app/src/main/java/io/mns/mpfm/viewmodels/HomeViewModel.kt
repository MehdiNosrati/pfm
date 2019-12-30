package io.mns.mpfm.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.mns.mpfm.DataRepository
import io.mns.mpfm.PfmApplication
import io.mns.mpfm.db.entities.Transaction

class HomeViewModel(application: Application, private val dataRepository: DataRepository) : AndroidViewModel(application) {

    fun loadTransactions(): LiveData<List<Transaction>> {
        return dataRepository.transactions
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(@NonNull val application: Application) : ViewModelProvider.NewInstanceFactory() {
        private var mRepository: DataRepository = (application as PfmApplication).repository

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            //noinspection unchecked
            return (HomeViewModel(application, mRepository)) as T
        }

    }
}