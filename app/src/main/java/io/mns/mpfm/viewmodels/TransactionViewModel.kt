package io.mns.mpfm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.mns.mpfm.DataRepository
import io.mns.mpfm.PfmApplication
import io.mns.mpfm.db.entities.Transaction
import java.util.*

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var dataRepository: DataRepository = (application as PfmApplication).repository

    fun submit(title: String, value: Long) {
        val transaction = Transaction()
        transaction.let {
            it.date = Date()
            it.title = title
            it.type = Transaction.TransactionType.INCOME //TODO fix
            it.value = value
        }
        dataRepository.submit(transaction)
    }
}