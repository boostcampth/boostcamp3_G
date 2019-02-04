package com.boostcamp.dreampicker.presentation.user.userList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ItemUserListBinding;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.presentation.BaseRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends BaseRecyclerViewAdapter<User, UserListAdapter.ViewHolder> {

    @Override
    protected void onBindView(ViewHolder holder, int position) {

        holder.binding.setUser(itemList.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ItemUserListBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
