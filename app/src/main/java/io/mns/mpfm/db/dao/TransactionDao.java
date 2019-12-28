package io.mns.mpfm.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.mns.mpfm.db.entities.Transaction;

@Dao
public interface TransactionDao {

    @Query("select * from transaction_table")
    LiveData<List<Transaction>> loadTransactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(Transaction t);

}
