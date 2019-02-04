package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.presentation.BaseViewModel;
import com.boostcamp.dreampicker.utils.Constant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedViewModel extends BaseViewModel {
    private final FeedDataSource feedRepository;
    private final MutableLiveData<List<Feed>> feedList = new MutableLiveData<>();

    public FeedViewModel(@NonNull FeedDataSource feedRepository) {
        this.feedRepository = feedRepository;
        loadFeedList();
    }

    // Todo : Firebase 투표 반영
    public void vote(@NonNull final VoteResult result) {
        final Feed newFeed = new Feed(result.getFeed());
        final int flag = result.getFlag();

        if (newFeed.getVoteFlag() != Constant.NONE) {
            if (newFeed.getVoteFlag() != flag) {
                if (flag == Constant.LEFT) {
                    newFeed.setLeftCount(newFeed.getLeftCount() + 1);
                    newFeed.setRightCount(newFeed.getRightCount() - 1);
                } else if (flag == Constant.RIGHT) {
                    newFeed.setRightCount(newFeed.getRightCount() + 1);
                    newFeed.setLeftCount(newFeed.getLeftCount() - 1);
                }
            }
        } else {
            if (flag == Constant.LEFT) {
                newFeed.setLeftCount(newFeed.getLeftCount() + 1);
            } else if (flag == Constant.RIGHT) {
                newFeed.setRightCount(newFeed.getRightCount() + 1);
            }
        }
        newFeed.setVoteFlag(flag);
        updateVote(newFeed);
    }

    private void updateVote(@NonNull Feed feed) {
        final List<Feed> feedList = this.feedList.getValue();
        if(feedList != null) {
            for(int i=0; i<feedList.size(); i++) {
                final Feed f = feedList.get(i);
                if(f.getId().equals(feed.getId())) {
                    feedList.set(i, feed);
                    break;
                }
            }
        }
        this.feedList.postValue(feedList);
    }

    private void loadFeedList() {
        isLoading.set(true);

        addDisposable(feedRepository.getAllFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((feedList) -> {
                    isLoading.set(false);
                    this.feedList.setValue(feedList);
                }));
    }

    public LiveData<List<Feed>> getFeedList() {
        return feedList;
    }
}
