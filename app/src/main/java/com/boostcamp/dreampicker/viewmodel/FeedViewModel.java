package com.boostcamp.dreampicker.viewmodel;

import android.util.Log;

import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.data.source.feed.FeedDataSource;
import com.boostcamp.dreampicker.data.source.feed.FeedRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedViewModel extends BaseViewModel {
    private FeedDataSource feedRepository;
    private MutableLiveData<List<Feed>> feeds = new MutableLiveData<>();

    public FeedViewModel() {
        feedRepository = FeedRepository.getInstance();
        initViewModel();
    }

    private void initViewModel() {
        addDisposable(feedRepository.getAllFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feeds::setValue, (e) -> Log.d("SS", e.toString())));
    }

    public LiveData<List<Feed>> getFeeds() {
        return feeds;
    }

}
