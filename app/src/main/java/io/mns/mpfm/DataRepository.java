package io.mns.mpfm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

import io.mns.mpfm.db.AppDatabase;
import io.mns.mpfm.db.entities.Transaction;

/**
 * Repository handling the work with transactions and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<Transaction>> mObservableTransactions;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableTransactions = new MediatorLiveData<>();

        mObservableTransactions.addSource(mDatabase.transactionDao().loadTransactions(),
                transactionEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableTransactions.postValue(transactionEntities);
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
    public LiveData<List<Transaction>> getTransactions() {
        return mObservableTransactions;
    }

    public void submit(Transaction transaction) {
        mDatabase.getExecutor().execute(() ->
                mDatabase.transactionDao().insertTransaction(transaction));
        if (mObservableTransactions.getValue() != null && mObservableTransactions.getValue().size() == 0)
            mObservableTransactions.postValue(new ArrayList<Transaction>() {{
                add(transaction);
            }});
    }
}

