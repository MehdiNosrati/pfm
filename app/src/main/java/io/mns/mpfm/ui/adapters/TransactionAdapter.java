package io.mns.mpfm.ui.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.mns.mpfm.model.Transaction;
import io.mns.mpfm.ui.TransactionClickCallback;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Transaction> mTransactions;
    private TransactionClickCallback mTransactionClickCallback;

    public TransactionAdapter(TransactionClickCallback clickCallback) {
        this.mTransactionClickCallback = clickCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTransactions == null ? 0 : mTransactions.size();
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
