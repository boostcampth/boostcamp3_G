package com.boostcamp.dreampicker.presentation.profile;

import com.boostcamp.dreampicker.data.model.FeedPrevious;
import com.boostcamp.dreampicker.data.paging.datasource.ProfileFeedDataSource;
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
public class ProfileFeedViewModel extends BaseViewModel {
    private final int PAGE_SIZE = 20;

    @NonNull
    public LiveData<PagedList<FeedPrevious>> feedPagedList;
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    private ProfileFeedViewModel(@NonNull final FeedRepository repository,
                                 @NonNull final String userId) {

        final PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(2) // 아래서 2번째 아이템이 보일 때 로딩시작
                .setEnablePlaceholders(false) // 전체 사이즈 알고있는 경우(Countable List)에만 유효함
                .build();

        this.feedPagedList = new LivePagedListBuilder<>(
                new ProfileFeedDataSource.Factory(repository, userId), config)
                .build();
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }

    static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private final FeedRepository repository;
        @NonNull
        private final String userId;

        Factory(@NonNull FeedRepository repository, @NonNull String userId) {
            this.repository = repository;
            this.userId = userId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ProfileFeedViewModel.class)) {
                //noinspection unchecked
                return (T) new ProfileFeedViewModel(repository, userId);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
