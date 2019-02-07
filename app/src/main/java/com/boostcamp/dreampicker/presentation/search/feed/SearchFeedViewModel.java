package com.boostcamp.dreampicker.presentation.search.feed;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * TODO.
 * 1. 에러처리
 * 2. 로딩 중 표시
 * 3. 새로고침(refresh layout)
 */
public class SearchFeedViewModel extends BaseViewModel {
    private final int PAGE_SIZE = 20;

    @NonNull
    private FeedRepository repository;

    @NonNull
    public LiveData<PagedList<Feed>> feedPagedList;
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    private final PagedList.Config config = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(2) // 아래서 2번째 아이템이 보일 때 로딩시작
            .setEnablePlaceholders(false) // 전체 사이즈 알고있는 경우(Countable List)에만 유효함
            .build();

    private SearchFeedViewModel(@NonNull FeedRepository repository) {
        this.repository = repository;
        loadSearchFeed("");
    }

    public void loadSearchFeed(@NonNull String searchKey) {
        this.feedPagedList = new LivePagedListBuilder<>(
                new SearchFeedPagedDataSource.Factory(repository, searchKey),
                config
        ).build();
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
