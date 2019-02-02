package com.boostcamp.dreampicker.viewmodel;

import android.view.View;

import com.boostcamp.dreampicker.data.source.feed.FeedDataSource;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.view.feed.drag.VoteContainerDragListener;
import com.boostcamp.dreampicker.view.feed.drag.VoteIconTouchListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedViewModel extends BaseViewModel {
    private static final int NONE = 0, LEFT = 1, RIGHT = 2;

    @IntDef({NONE, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface VoteFlag { }

    private final FeedDataSource feedRepository;

    private final MutableLiveData<List<Feed>> feeds = new MutableLiveData<>();

    public View.OnTouchListener touchListener = new VoteIconTouchListener();
    public View.OnDragListener leftDragListener, rightDragListener;

    public FeedViewModel(FeedDataSource feedRepository) {
        this.feedRepository = feedRepository;

        leftDragListener = new VoteContainerDragListener((id) -> vote(id, LEFT));
        rightDragListener = new VoteContainerDragListener((id) -> vote(id, RIGHT));
    }

    // Todo : Firebase 투표 반영
    private void vote(final String id, @VoteFlag final int flag) {
        final List<Feed> feedList = feeds.getValue();

        if (feedList != null) {
            final Feed feed = getFeedById(feedList, id);
            if (feed != null) {
                if(feed.getVoteFlag() != NONE) { // 투표 업데이트
                    updateVote(feedList, feed, flag);
                    return;
                }
                if (flag == LEFT) {
                    feed.setLeftCount(feed.getLeftCount()+1);
                } else if (flag == RIGHT) {
                    feed.setRightCount(feed.getRightCount()+1);
                }
                applyFeedState(feedList, feed, flag);
            }
        } else { // feedList == null

        }
    }

    private void updateVote(List<Feed> feedList, Feed feed, @VoteFlag final int flag) {
        if(feed.getVoteFlag() != flag) {
            if(flag == LEFT) { // 왼쪽으로 변경 투표
                feed.setLeftCount(feed.getLeftCount()+1);
                feed.setRightCount(feed.getRightCount()-1);
            } else if (flag == RIGHT) { // 오른쪽으로 변경 투표
                feed.setRightCount(feed.getRightCount()+1);
                feed.setLeftCount(feed.getLeftCount()-1);
            }
            applyFeedState(feedList, feed, flag);
        }
    }

    private void applyFeedState(List<Feed> feedList, Feed feed, @VoteFlag final int flag) {
        feed.setVoteFlag(flag);
        feedList.set(feedList.indexOf(feed), feed);
        feeds.postValue(feedList);
    }

    private Feed getFeedById(final List<Feed> feedList, final String id) {
        for(Feed feed : feedList) {
            if(feed.getId().equals(id)) {
                return feed;
            }
        }
        return null;
    }

    public Feed getFeedAt(int position) {
        if(feeds.getValue() != null && position >= 0) {
            return feeds.getValue().get(position);
        }
        return null;
    }

    public LiveData<List<Feed>> getFeeds() {
        isLoading.set(true);

        addDisposable(feedRepository.getAllFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((feedList) -> {
                    isLoading.set(false);
                    feeds.setValue(feedList);
                }));

        return feeds;
    }
}
