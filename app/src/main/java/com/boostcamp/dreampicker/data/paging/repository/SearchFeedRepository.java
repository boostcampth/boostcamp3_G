package com.boostcamp.dreampicker.data.paging.repository;

import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.data.paging.datasource.SearchFeedDataSource;
import com.boostcamp.dreampicker.data.paging.repository.response.PagingSource;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class SearchFeedRepository {

    @NonNull
    private FeedRepository repository;

    public SearchFeedRepository(@NonNull FeedRepository repository) {
        this.repository = repository;
    }

    public PagingSource<LegacyFeed, SearchFeedDataSource> addSearchFeedList(@NonNull String searchKey) {

        // 데이터 소스 팩토리 생성
        SearchFeedDataSource.Factory factory =
                new SearchFeedDataSource.Factory(repository, searchKey);

        // Config 설정
        final int PAGE_SIZE = 20;
        final PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(2) // 아래서 2번째 아이템이 보일 때 로딩시작
                .setEnablePlaceholders(false)
                .build();

        // PagedList LiveData 생성
        LiveData<PagedList<LegacyFeed>> userPagedList =
                new LivePagedListBuilder<>(factory, config).build();

        return new PagingSource<>(
                userPagedList,
                factory.sourceLiveData);
    }
}
