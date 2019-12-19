package io.mns.mpfm.db.converters;

import androidx.room.TypeConverter;

import io.mns.mpfm.db.entities.TransactionEntity.TransactionType;

import static io.mns.mpfm.db.entities.TransactionEntity.TransactionType.EXPENSE;
import static io.mns.mpfm.db.entities.TransactionEntity.TransactionType.INCOME;

public class TransactionTypeConverter {
    @TypeConverter
    public TransactionType toTransactionType(int type) {
        if (type == 0) {
            return INCOME;
        } else {
            return EXPENSE;
        }
    }

    @TypeConverter
    public int toInt(TransactionType type) {
        if (type == INCOME) {
            return 0;
        } else {
            return 1;
        }
    }
}
