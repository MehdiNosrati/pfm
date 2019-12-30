package io.mns.mpfm.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.mns.mpfm.db.entities.Balance;

public class SharedPreferencesHelper {
    public static void updateBalance(Context context, Balance balance) {
        SharedPreferences.Editor editor =
                context.getSharedPreferences(Keys.KEY_BALANCE_PREFS, Context.MODE_PRIVATE).edit();
        editor.putLong(Keys.KEY_REMAINING, balance.getRemaining());
        editor.putLong(Keys.KEY_TOTAL_INCOME, balance.getIncome());
        editor.putLong(Keys.KEY_TOTAL_EXPENSE, balance.getExpense());
        editor.apply();
    }

    public static Balance getBalance(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(Keys.KEY_BALANCE_PREFS, Context.MODE_PRIVATE);
        Long remaining = preferences.getLong(Keys.KEY_REMAINING, 0L);
        Long income = preferences.getLong(Keys.KEY_TOTAL_INCOME, 0L);
        Long expense = preferences.getLong(Keys.KEY_TOTAL_EXPENSE, 0L);
        return new Balance(remaining, income, expense);

    }

    private class Keys {
        private static final String KEY_BALANCE_PREFS = "balance";
        private static final String KEY_REMAINING = "balance_remaining";
        private static final String KEY_TOTAL_EXPENSE = "balance_expense";
        private static final String KEY_TOTAL_INCOME = "balance_income";
    }
}
