package io.mns.mpfm.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.ItemTransactionExpenseBinding;
import io.mns.mpfm.databinding.ItemTransactionIncomeBinding;
import io.mns.mpfm.db.entities.Transaction;
import io.mns.mpfm.ui.callbacks.TransactionClickCallback;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INCOME_VIEW = 0;
    private static final int EXPENSE_VIEW = 1;
    private List<Transaction> mTransactions;
    private TransactionClickCallback mTransactionClickCallback;

    public TransactionAdapter(TransactionClickCallback clickCallback) {
        this.mTransactionClickCallback = clickCallback;
    }

    public void setData(List<Transaction> transactions) {
        if (mTransactions == null) {
            mTransactions = transactions;
            notifyItemRangeInserted(0, transactions.size());
        } else {
            DiffUtil.Callback callback = new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTransactions.size();
                }

                @Override
                public int getNewListSize() {
                    return transactions.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTransactions.get(oldItemPosition).getId() == transactions.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTransactions.get(oldItemPosition).getTitle().equals(transactions.get(newItemPosition).getTitle())
                            && mTransactions.get(oldItemPosition).getValue() == transactions.get(newItemPosition).getValue()
                            && mTransactions.get(oldItemPosition).getType() == transactions.get(newItemPosition).getType();
                }
            };
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
            mTransactions = transactions;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView;
        if (viewType == INCOME_VIEW) {
            ItemTransactionIncomeBinding binding;
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_transaction_income, parent, false);
            return new IncomeViewHolder(binding, mTransactionClickCallback);
        } else {
            ItemTransactionExpenseBinding binding;
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_transaction_expense, parent, false);
            return new ExpenseViewHolder(binding, mTransactionClickCallback);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mTransactions.get(position).getType() == Transaction.TransactionType.INCOME) {
            ((IncomeViewHolder) holder).bind(mTransactions.get(position));
        } else {
            ((ExpenseViewHolder) holder).bind(mTransactions.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTransactions.get(position).getType() == Transaction.TransactionType.INCOME) {
            return INCOME_VIEW;
        } else {
            return EXPENSE_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        return mTransactions == null ? 0 : mTransactions.size();
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {
        ItemTransactionIncomeBinding mBinding;
        TransactionClickCallback mCallback;
        IncomeViewHolder(@NonNull ItemTransactionIncomeBinding binding, TransactionClickCallback callback) {
            super(binding.getRoot());
            mBinding = binding;
            mCallback = callback;
        }

        void bind(Transaction t) {
            mBinding.setData(t);
            mBinding.setCallback(mCallback);
        }
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        ItemTransactionExpenseBinding mBinding;
        TransactionClickCallback mCallback;

        ExpenseViewHolder(@NonNull ItemTransactionExpenseBinding binding, TransactionClickCallback callback) {
            super(binding.getRoot());
            mBinding = binding;
            this.mCallback = callback;
        }

        void bind(Transaction t) {
            mBinding.setData(t);
            mBinding.setCallback(mCallback);
        }
    }
}
