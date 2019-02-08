package com.boostcamp.dreampicker.data.paging.repository;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.paging.datasource.SearchUserDataSource;
import com.boostcamp.dreampicker.data.paging.repository.response.PagingSource;
import com.boostcamp.dreampicker.data.source.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class SearchUserRepository {

    @NonNull
    private UserRepository repository;

    public SearchUserRepository(@NonNull UserRepository repository) {
        this.repository = repository;
    }

    public PagingSource<User, SearchUserDataSource> addSearchUserList(@NonNull String searchKey) {

        // 데이터 소스 팩토리 생성
        SearchUserDataSource.Factory factory =
                new SearchUserDataSource.Factory(repository, searchKey);

        // Config 설정
        final int PAGE_SIZE = 20;
        final PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(2) // 아래서 2번째 아이템이 보일 때 로딩시작
                .setEnablePlaceholders(false)
                .build();

        // PagedList LiveData 생성
        LiveData<PagedList<User>> userPagedList =
                new LivePagedListBuilder<>(factory, config).build();

        return new PagingSource<>(
                userPagedList,
                factory.sourceLiveData);
    }
}
