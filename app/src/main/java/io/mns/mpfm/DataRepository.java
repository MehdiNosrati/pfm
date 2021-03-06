package io.mns.mpfm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.mns.mpfm.db.AppDatabase;
import io.mns.mpfm.db.entities.Tag;
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
                transactionEntities ->
                        mObservableTransactions.postValue(transactionEntities));
    }

    static DataRepository getInstance(final AppDatabase database) {
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
    }

    public LiveData<Transaction> getTransactionById(int id) {
        MutableLiveData<Transaction> transaction = new MutableLiveData<>();
        mDatabase.getExecutor().execute(() ->
                transaction.postValue(mDatabase.transactionDao().getTransactionById(id)));
        return transaction;
    }

    public void removeTransaction(Transaction transaction) {
        mDatabase.getExecutor().execute(() ->
                mDatabase.transactionDao().removeTransaction(transaction));
    }

    public LiveData<List<Transaction>> filterTransactionsByType(int type) {
        MediatorLiveData<List<Transaction>> transactions = new MediatorLiveData<>();
        transactions.addSource(mDatabase.transactionDao().filterByType(type), transactions::postValue);
        return transactions;
    }

    @NotNull
    public LiveData<List<Tag>> findTags() {
        MediatorLiveData<List<Tag>> tags = new MediatorLiveData<>();
        tags.addSource(mDatabase.transactionDao().findTags(), tags::postValue);
        return tags;
    }
}

