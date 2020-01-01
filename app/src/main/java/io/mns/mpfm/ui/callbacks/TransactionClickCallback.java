package io.mns.mpfm.ui.callbacks;

import io.mns.mpfm.db.entities.Transaction;

public interface TransactionClickCallback {
    void onClick(Transaction transaction);
}
