package io.mns.mpfm.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.mns.mpfm.DataRepository
import io.mns.mpfm.PfmApplication
import io.mns.mpfm.db.entities.Balance
import io.mns.mpfm.db.entities.Tag
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
        submit(transaction)
    }

    fun submit(transaction: Transaction, title: String, value: Long) {
        transaction.let {
            it.title = title
            it.type =
                    if (value > 0)
                        Transaction.TransactionType.INCOME
                    else
                        Transaction.TransactionType.EXPENSE
            it.value = value
        }
        submit(transaction)
    }

    private fun submit(transaction: Transaction) {
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

    fun updateBalance(context: Context, value: Long, previousTransaction: Transaction?) {
        val balance = Balance.getInstance(context)
        if (previousTransaction != null) {
            balance.remaining -= previousTransaction.value
            if (previousTransaction.value > 0)
                balance.income -= previousTransaction.value
            else
                balance.expense -= -(previousTransaction.value)
        }
        updateBalance(context, value)
    }

    fun getTransactionById(id: Int): LiveData<Transaction> {
        return dataRepository.getTransactionById(id)
    }

    fun removeTransaction(transaction: Transaction) {
        dataRepository.removeTransaction(transaction)
    }

    fun findTags(): LiveData<List<Tag>> {
        return dataRepository.findTags()
    }
}