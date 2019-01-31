package com.boostcamp.dreampicker.viewmodel;

import com.boostcamp.dreampicker.model.Feed;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

public class FeedVoteViewModel {
    public enum VotePosition {NONE, LEFT, RIGHT}
    private VotePosition position = VotePosition.NONE;

    private final Feed feed;

    private final ObservableInt totalVotedCount = new ObservableInt(0);
    private final ObservableInt leftVotedCount = new ObservableInt(0);
    private final ObservableInt rightVotedCount = new ObservableInt(0);

    private final ObservableBoolean isVoted = new ObservableBoolean(false);

    public FeedVoteViewModel(@NonNull Feed feed) {
        this.feed = feed;

        initViewModel();
    }

    private void initViewModel() {
        leftVotedCount.set(feed.leftVotedCount());
        rightVotedCount.set(feed.rightVotedCount());
        totalVotedCount.set(leftVotedCount.get() + rightVotedCount.get());
    }

    public void vote(VotePosition position) {
        if(isVoted.get()) {
            updateVote(position);
        } else {
            isVoted.set(true);
            this.position = position;

            if (position == VotePosition.LEFT) {
                leftVotedCount.set(leftVotedCount.get() + 1);
            } else if(position == VotePosition.RIGHT) {
                rightVotedCount.set(rightVotedCount.get() + 1);
            }

            totalVotedCount.set(leftVotedCount.get() + rightVotedCount.get());
        }
    }
    private void updateVote(VotePosition position) {
        if(this.position != position) {
            if(position == VotePosition.LEFT) {
                rightVotedCount.set(rightVotedCount.get()-1);
                leftVotedCount.set(leftVotedCount.get()+1);
            } else if(position == VotePosition.RIGHT) {
                leftVotedCount.set(leftVotedCount.get()-1);
                rightVotedCount.set(rightVotedCount.get()+1);
            }
            this.position = position;
        }
    }

    public ObservableInt getTotalVotedCount() {
        return totalVotedCount;
    }

    public ObservableInt getLeftVotedCount() {
        return leftVotedCount;
    }

    public ObservableInt getRightVotedCount() {
        return rightVotedCount;
    }

    public ObservableBoolean getIsVoted() {
        return isVoted;
    }
}
