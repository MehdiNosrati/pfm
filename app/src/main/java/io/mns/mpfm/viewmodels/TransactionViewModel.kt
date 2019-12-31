package io.mns.mpfm.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import io.mns.mpfm.DataRepository
import io.mns.mpfm.PfmApplication
import io.mns.mpfm.db.entities.Balance
import io.mns.mpfm.db.entities.Transaction
import io.mns.mpfm.utils.SharedPreferencesHelper
import java.text.SimpleDateFormat
import java.util.*

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var dataRepository: DataRepository = (application as PfmApplication).repository
    private val dateFormatter = SimpleDateFormat("MMMM dd", Locale.US)

    fun submit(title: String, value: Long) {
        val transaction = Transaction()
        transaction.let {
            it.date = Date()
            it.title = title
            it.type =
                    if (value > 0)
                        Transaction.TransactionType.INCOME
                    else
                        Transaction.TransactionType.EXPENSE
            it.value = value
            it.humanReadableDate = humanReadableDate(it.date)
        }
        dataRepository.submit(transaction)
    }

    private fun humanReadableDate(date: Date): String {
        return dateFormatter.format(date)
    }

    fun updateBalance(context: Context, value: Long) {
        val balance = Balance.getInstance(context)
        balance.remaining += value
        if (value > 0)
            balance.income += value
        else
            balance.expense += (-value)
        SharedPreferencesHelper.updateBalance(context, balance)
    }
}