package io.mns.mpfm.model;

import java.util.Date;

import io.mns.mpfm.db.entities.TransactionEntity;

public interface Transaction {
    int getId();
    long getValue();
    String getTitle();
    Date getDate();
    TransactionEntity.TransactionType getType();
}
