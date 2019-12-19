package io.mns.mpfm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import io.mns.mpfm.db.AppDatabase;
import io.mns.mpfm.db.entities.TransactionEntity;

/**
 * Repository handling the work with transactions and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<TransactionEntity>> mObservabletransactions;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservabletransactions = new MediatorLiveData<>();

        mObservabletransactions.addSource(mDatabase.transactionDao().loadTransactions(),
                transactionEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservabletransactions.postValue(transactionEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of transactions from the database and get notified when the data changes.
     */
    public LiveData<List<TransactionEntity>> gettransactions() {
        return mObservabletransactions;
    }
}

