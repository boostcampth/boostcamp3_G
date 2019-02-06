package com.boostcamp.dreampicker.presentation;

import com.boostcamp.dreampicker.presentation.listener.OnItemClickListener;
import com.boostcamp.dreampicker.presentation.listener.OnItemLongClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BasePagedListAdapter<T, VH extends RecyclerView.ViewHolder> extends PagedListAdapter<T, VH> {

    protected BasePagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    public BasePagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,
                                @Nullable OnItemClickListener<T> onItemClickListener) {
        super(diffCallback);
        this.onItemClickListener = onItemClickListener;
    }

    @Nullable
    protected OnItemClickListener<T> onItemClickListener;
    @Nullable
    protected OnItemLongClickListener<T> onItemLongClickListener;

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {

        holder.itemView.setOnClickListener(__ -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getItem(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnLongClickListener(__ -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(getItem(holder.getAdapterPosition()));
            }
            return false;
        });

        onBindView(holder, position);
    }

    protected abstract void onBindView(VH holder, int position);

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
