package io.mns.mpfm.db.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import io.mns.mpfm.model.Transaction;

@Entity(tableName = "transaction_table")
public class TransactionEntity extends BaseObservable implements Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long value;
    private String title;
    private TransactionType type;

    @Ignore
    public TransactionEntity(int id, long value, String title, TransactionType type, Date date) {
        this.id = id;
        this.value = value;
        this.title = title;
        this.type = type;
        this.date = date;
    }

    private Date date;

    @Override
    public int getId() {
        return this.id;
    }

    public TransactionEntity() {
    }

    @Bindable
    @Override
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Bindable
    @Override
    public String getTitle() {
        return title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Bindable
    @Override
    public Date getDate() {
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public enum TransactionType {
        EXPENSE, INCOME
    }
}
