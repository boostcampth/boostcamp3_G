package com.boostcamp.dreampicker.presentation.search.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.databinding.ItemSearchUserBinding;
import com.boostcamp.dreampicker.presentation.BasePagedListAdapter;
import com.boostcamp.dreampicker.presentation.listener.OnItemClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SearchUserAdapter extends BasePagedListAdapter<User, SearchUserAdapter.ViewHolder> {

    public SearchUserAdapter(@Nullable OnItemClickListener<User> onItemClickListener) {
        super(DIFF_CALLBACK, onItemClickListener);
    }

    @Override
    protected void onBindView(ViewHolder holder, int position) {
        final User user = getItem(position);
        holder.bindTo(user);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_user, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemSearchUserBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bindTo(User user) {
            binding.setUser(user);
        }
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<User>() {
                @Override
                public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                    return oldItem.equals(newItem);
                }
            };

}
