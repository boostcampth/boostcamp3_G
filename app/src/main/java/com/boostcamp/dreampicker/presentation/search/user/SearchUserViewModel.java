package com.boostcamp.dreampicker.presentation.search.user;

import com.boostcamp.dreampicker.data.model.LegacyUser;
import com.boostcamp.dreampicker.data.paging.datasource.SearchUserDataSource;
import com.boostcamp.dreampicker.data.paging.repository.SearchUserRepository;
import com.boostcamp.dreampicker.data.paging.repository.response.PagingSource;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;
import com.boostcamp.dreampicker.presentation.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

public class SearchUserViewModel extends BaseViewModel {

    @NonNull
    private SearchUserRepository repository;

    @NonNull
    private LiveData<PagingSource<LegacyUser, SearchUserDataSource>> pagingSource;

    @NonNull
    public LiveData<PagedList<LegacyUser>> userPagedList;
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<String> searchKey = new MutableLiveData<>();

    private SearchUserViewModel(@NonNull UserRepository repository) {
        this.repository = new SearchUserRepository(repository);
        this.searchKey.setValue("");

        // searchKey 바뀔때마다 repository.addSearchUserList(searchKey) 요청
        // 새로운 pagedList, dataSource 만들어져 pagingSource 에 반환
        this.pagingSource = Transformations.map(searchKey, this.repository::addSearchUserList);

        // pagingSource 가 바뀌면 pagedList를 받아와서 observer에게 전송
        this.userPagedList = Transformations.switchMap(pagingSource, PagingSource::getPagedList);
    }

    public void loadSearchUser(@NonNull String searchKey) {
        this.searchKey.setValue(searchKey);
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
