package com.boostcamp.dreampicker.data.paging.repository.response;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

public class PagingSource<T, DS extends DataSource> {
    private LiveData<PagedList<T>> pagedList;
    private MutableLiveData<DS> dataSource;

    public PagingSource(LiveData<PagedList<T>> pagedList,
                        MutableLiveData<DS> dataSource) {
        this.pagedList = pagedList;
        this.dataSource = dataSource;
    }

    public LiveData<PagedList<T>> getPagedList() {
        return pagedList;
    }

    public void setPagedList(LiveData<PagedList<T>> pagedList) {
        this.pagedList = pagedList;
    }

    public MutableLiveData<DS> getDataSource() {
        return dataSource;
    }

    public void setDataSource(MutableLiveData<DS> dataSource) {
        this.dataSource = dataSource;
    }
}
