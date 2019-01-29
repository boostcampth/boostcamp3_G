package com.boostcamp.dreampicker.viewmodel;

import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.source.feed.FeedDataSource;
import com.boostcamp.dreampicker.model.source.feed.FeedRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedViewModel extends BaseViewModel {
    private FeedDataSource feedRepository;

    private MutableLiveData<List<Feed>> _feeds;
    private LiveData<List<Feed>> feeds;

    @Override
    protected void initViewModel() {
        feedRepository = FeedRepository.getInstance();
        _feeds = new MutableLiveData<>();
        feeds = _feeds;

        addDisposable(feedRepository.getAllFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_feeds::setValue));
    }

    public LiveData<List<Feed>> getFeeds() {
        return feeds;
    }

}
