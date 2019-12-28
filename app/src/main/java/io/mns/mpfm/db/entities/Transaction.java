package io.mns.mpfm.db.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transaction_table")
public class Transaction extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long value;
    private String title;
    private TransactionType type;

    @Ignore
    public Transaction(int id, long value, String title, TransactionType type, Date date) {
        this.id = id;
        this.value = value;
        this.title = title;
        this.type = type;
        this.date = date;
    }

    private Date date;

    public int getId() {
        return this.id;
    }

    public Transaction() {
    }

    @Bindable
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Bindable
    public Date getDate() {
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
