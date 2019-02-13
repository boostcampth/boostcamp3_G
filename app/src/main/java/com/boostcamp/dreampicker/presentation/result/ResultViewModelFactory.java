package com.boostcamp.dreampicker.presentation.result;

import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ResultViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final VotedFeedDao votedFeedDao;

    ResultViewModelFactory(@NonNull VotedFeedDao votedFeedDao) {
        this.votedFeedDao = votedFeedDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ResultViewModel.class)) {
            //noinspection unchecked
            return (T) new ResultViewModel(votedFeedDao);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
