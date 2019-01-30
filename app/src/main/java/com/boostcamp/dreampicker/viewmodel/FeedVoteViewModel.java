package com.boostcamp.dreampicker.viewmodel;

import com.boostcamp.dreampicker.model.Feed;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

public class FeedVoteViewModel extends BaseViewModel {
    private final Feed feed;

    private final ObservableInt totalVoteCount = new ObservableInt(0);
    private final ObservableInt leftVotedCount = new ObservableInt(0);
    private final ObservableInt rightVotedCount = new ObservableInt(0);

    private final ObservableBoolean isShowResult = new ObservableBoolean(false);

    public FeedVoteViewModel(@NonNull Feed feed) {
        this.feed = feed;

        initViewModel();
    }

    private void initViewModel() {
        leftVotedCount.set(feed.leftVotedCount());
        rightVotedCount.set(feed.rightVotedCount());

        totalVoteCount.set(leftVotedCount.get() + rightVotedCount.get());

        isShowResult.set(true);
    }

    public ObservableInt getTotalVoteCount() {
        return totalVoteCount;
    }

    public ObservableInt getLeftVotedCount() {
        return leftVotedCount;
    }

    public ObservableInt getRightVotedCount() {
        return rightVotedCount;
    }

    public ObservableBoolean getIsShowResult() {
        return isShowResult;
    }

}
