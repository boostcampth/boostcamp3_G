package com.boostcamp.dreampicker.presentation.feed.voted;

import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class VotedViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final VotedFeedDao votedFeedDao;

    public VotedViewModelFactory(@NonNull VotedFeedDao votedFeedDao) {
        this.votedFeedDao = votedFeedDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(VotedViewModel.class)) {
            //noinspection unchecked
            return (T) new VotedViewModel(votedFeedDao);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
