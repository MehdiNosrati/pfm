package io.mns.mpfm.db.entities

import android.content.Context
import io.mns.mpfm.utils.SharedPreferencesHelper

data class Balance(var remaining: Long, var income: Long, var expense: Long) {
    companion object {
        private var balance: Balance? = null
        fun getInstance(context: Context): Balance {
            if (balance == null) {
                balance = SharedPreferencesHelper.getBalance(context)
            }
            return balance!!
        }
    }
}