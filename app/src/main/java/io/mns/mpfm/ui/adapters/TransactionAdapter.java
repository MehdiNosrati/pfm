package io.mns.mpfm.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.ItemTransactionExpenseBinding;
import io.mns.mpfm.databinding.ItemTransactionIncomeBinding;
import io.mns.mpfm.db.entities.TransactionEntity;
import io.mns.mpfm.model.Transaction;
import io.mns.mpfm.ui.TransactionClickCallback;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INCOME_VIEW = 0;
    private static final int EXPENSE_VIEW = 1;
    private List<Transaction> mTransactions;
    private TransactionClickCallback mTransactionClickCallback;

    public TransactionAdapter(TransactionClickCallback clickCallback) {
        this.mTransactionClickCallback = clickCallback;
    }

    public void setData(List<Transaction> transactions) {
        this.mTransactions = transactions;
        notifyItemRangeInserted(0, transactions.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView;
        if (viewType == INCOME_VIEW) {
            ItemTransactionIncomeBinding binding;
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_transaction_income, parent, false);
            return new IncomeViewHolder(binding);
        } else {
            ItemTransactionExpenseBinding binding;
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_transaction_expense, parent, false);
            return new ExpenseViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mTransactions.get(position).getType() == TransactionEntity.TransactionType.INCOME) {
            ((IncomeViewHolder) holder).bind(((TransactionEntity) mTransactions.get(position)));
        } else {
            ((ExpenseViewHolder) holder).bind(((TransactionEntity) mTransactions.get(position)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTransactions.get(position).getType() == TransactionEntity.TransactionType.INCOME) {
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
        IncomeViewHolder(@NonNull ItemTransactionIncomeBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(TransactionEntity t) {
            mBinding.setData(t);
        }
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        ItemTransactionExpenseBinding mBinding;

        ExpenseViewHolder(@NonNull ItemTransactionExpenseBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(TransactionEntity t) {
            mBinding.setData(t);
        }
    }
}
