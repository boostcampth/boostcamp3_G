package com.boostcamp.dreampicker.presentation.search.feed;

import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.data.paging.datasource.SearchFeedDataSource;
import com.boostcamp.dreampicker.data.paging.repository.SearchFeedRepository;
import com.boostcamp.dreampicker.data.paging.repository.response.PagingSource;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

/**
 * TODO.
 * 1. 에러처리
 * 2. 로딩 중 표시
 * 3. 새로고침(refresh layout)
 */
public class SearchFeedViewModel extends BaseViewModel {

    @NonNull
    private SearchFeedRepository repository;
    @NonNull
    private LiveData<PagingSource<LegacyFeed, SearchFeedDataSource>> pagingSource;

    @NonNull
    public LiveData<PagedList<LegacyFeed>> feedPagedList;
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<String> searchKey = new MutableLiveData<>();

    private SearchFeedViewModel(@NonNull FeedRepository repository) {
        this.repository = new SearchFeedRepository(repository);
        this.searchKey.setValue("");

        this.pagingSource = Transformations.map(searchKey, this.repository::addSearchFeedList);
        this.feedPagedList = Transformations.switchMap(pagingSource, PagingSource::getPagedList);
    }

    public void loadSearchFeed(@NonNull String searchKey) {
        this.searchKey.setValue(searchKey);
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }


    static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private final FeedRepository repository;

        Factory(@NonNull FeedRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(SearchFeedViewModel.class)) {
                //noinspection unchecked
                return (T) new SearchFeedViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
