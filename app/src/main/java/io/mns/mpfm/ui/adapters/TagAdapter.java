package io.mns.mpfm.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.mns.mpfm.R;
import io.mns.mpfm.databinding.ItemTagBinding;
import io.mns.mpfm.db.entities.Tag;
import io.mns.mpfm.ui.callbacks.TagClickCallback;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private TagClickCallback mTagClickCallback;
    private List<Tag> mTags;

    public TagAdapter(TagClickCallback callback) {
        mTagClickCallback = callback;
    }

    public void setData(List<Tag> tags) {
        if (mTags == null) {
            mTags = tags;
        } else {
            DiffUtil.Callback callback = new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTags.size();
                }

                @Override
                public int getNewListSize() {
                    return tags.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTags.get(oldItemPosition).getTitle().equals(tags.get(newItemPosition).getTitle());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTags.get(oldItemPosition).getTitle().equals(tags.get(newItemPosition).getTitle());
                }
            };
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
            mTags = tags;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_tag, parent, false);
        return new TagViewHolder(binding, mTagClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.bind(mTags.get(position));
    }

    @Override
    public int getItemCount() {
        if (mTags != null)
            return mTags.size();
        else return 0;
    }

    static class TagViewHolder extends RecyclerView.ViewHolder {
        ItemTagBinding binding;
        TagClickCallback mCallback;

        TagViewHolder(ItemTagBinding binding, TagClickCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.mCallback = callback;
        }

        void bind(Tag tag) {
            binding.setTag(tag);
            binding.setCallback(mCallback);
        }
    }
}
