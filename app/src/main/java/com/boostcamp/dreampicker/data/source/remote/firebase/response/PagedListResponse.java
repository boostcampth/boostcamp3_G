package com.boostcamp.dreampicker.data.source.remote.firebase.response;

import java.util.List;

public class PagedListResponse<T> {
    private int start;
    private int display;
    private List<T> feedList;

    public PagedListResponse(int start, int display, List<T> feedList) {
        this.start = start;
        this.display = display;
        this.feedList = feedList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public List<T> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<T> feedList) {
        this.feedList = feedList;
    }
}
