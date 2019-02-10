package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class FeedViewModel extends ViewModel {
    @NonNull
    private final FeedRepository repository;

    FeedViewModel(@NonNull FeedRepository repository) {
        this.repository = repository;
    }


}
