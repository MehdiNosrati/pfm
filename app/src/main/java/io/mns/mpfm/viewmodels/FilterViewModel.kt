package io.mns.mpfm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.mns.mpfm.DataRepository
import io.mns.mpfm.PfmApplication
import io.mns.mpfm.db.entities.Transaction

class FilterViewModel(application: Application): AndroidViewModel(application) {
    private var dataRepository: DataRepository = (application as PfmApplication).repository

    fun filter(filterType: FilterType): LiveData<List<Transaction>> {
        return dataRepository.filterTransactionsByType(filterType.ordinal)
    }
}
enum class FilterType{
    INCOME, EXPENSE
}