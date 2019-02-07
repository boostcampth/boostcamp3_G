package com.boostcamp.dreampicker.presentation.search.user;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.paging.datasource.SearchUserDataSource;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class SearchUserViewModel extends BaseViewModel {
    private final int PAGE_SIZE = 20;

    @NonNull
    private UserRepository repository;

    @NonNull
    public LiveData<PagedList<User>> userPagedList;
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    private final PagedList.Config config = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(2) // 아래서 2번째 아이템이 보일 때 로딩시작
            .setEnablePlaceholders(false) // 전체 사이즈 알고있는 경우(Countable List)에만 유효함
            .build();

    private SearchUserViewModel(@NonNull UserRepository repository) {
        this.repository = repository;
        loadSearchUser("");
    }

    public void loadSearchUser(@NonNull String searchKey) {
        this.userPagedList = new LivePagedListBuilder<>(
                new SearchUserDataSource.Factory(repository, searchKey),
                config).build();
    }

    @NonNull
    public MutableLiveData<Throwable> getError() {
        return error;
    }

    static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private UserRepository repository;

        Factory(@NonNull UserRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(SearchUserViewModel.class)) {
                //noinspection unchecked
                return (T) new SearchUserViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
