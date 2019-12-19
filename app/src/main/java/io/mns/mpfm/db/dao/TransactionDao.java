package io.mns.mpfm.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.mns.mpfm.db.entities.TransactionEntity;

@Dao
public interface TransactionDao {

    @Query("select * from transaction_table")
    public LiveData<List<TransactionEntity>> loadTransactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTransaction(TransactionEntity t);

}
