package com.boostcamp.dreampicker.viewmodel;

import android.view.View;

import com.boostcamp.dreampicker.data.source.feed.FeedDataSource;
import com.boostcamp.dreampicker.data.source.feed.FeedMockDataSource;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.view.feed.drag.VoteContainerDragListener;
import com.boostcamp.dreampicker.view.feed.drag.VoteIconTouchListener;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FeedViewModel extends BaseViewModel {
    private enum VoteFlag {NONE, LEFT, RIGHT}

    private FeedDataSource feedRepository;

    private final MutableLiveData<List<Feed>> feeds = new MutableLiveData<>();

    public View.OnTouchListener touchListener = new VoteIconTouchListener();
    public View.OnDragListener leftDragListener, rightDragListener;


    public FeedViewModel() {
        feedRepository = new FeedMockDataSource();

        leftDragListener = new VoteContainerDragListener((id) -> vote(id, VoteFlag.LEFT));
        rightDragListener = new VoteContainerDragListener((id) -> vote(id, VoteFlag.RIGHT));

        addDisposable(feedRepository.getAllFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feeds::setValue));
    }

    // Todo : Firebase 투표 반영
    private void vote(final String id, final VoteFlag flag) {
        final List<Feed> feedList = feeds.getValue();

        if (feedList != null) {
            final Feed feed = getFeedById(feedList, id);
            if (feed != null) {
                if(feed.getVoteFlag() != VoteFlag.NONE.ordinal()) { // 투표 업데이트
                    updateVote(feedList, feed, flag);
                    return;
                }
                if (flag == VoteFlag.LEFT) {
                    feed.setLeftCount(feed.getLeftCount()+1);
                } else if (flag == VoteFlag.RIGHT) {
                    feed.setRightCount(feed.getRightCount()+1);
                }
                applyFeedState(feedList, feed, flag);
            }
        } else { // feedList == null

        }
    }

    private void updateVote(List<Feed> feedList, Feed feed, VoteFlag flag) {
        if(feed.getVoteFlag() != flag.ordinal()) {
            if(flag == VoteFlag.LEFT) { // 왼쪽으로 변경 투표
                feed.setLeftCount(feed.getLeftCount()+1);
                feed.setRightCount(feed.getRightCount()-1);
            } else if (flag == VoteFlag.RIGHT) { // 오른쪽으로 변경 투표
                feed.setRightCount(feed.getRightCount()+1);
                feed.setLeftCount(feed.getLeftCount()-1);
            }
            applyFeedState(feedList, feed, flag);
        }
    }

    private void applyFeedState(List<Feed> feedList, Feed feed, VoteFlag flag) {
        feed.setVoteFlag(flag.ordinal());
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
        return feeds;
    }
}
