package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.Image;
import com.boostcamp.dreampicker.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class FeedRemoteDataSource implements FeedDataSource {

    private static FeedRemoteDataSource feedRemoteDataSource = null;

    private FeedRemoteDataSource() {}

    public static FeedRemoteDataSource getInstance() {
        if(feedRemoteDataSource == null){
            synchronized (FeedRemoteDataSource.class){
                if(feedRemoteDataSource == null){
                    feedRemoteDataSource = new FeedRemoteDataSource();
                }
            }
        }
        return feedRemoteDataSource;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {

        // TODO. 파이어베이스에서 데이터 로딩하기
        return null;
    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {

        // TODO. 임시 코드 삭제
        List<Feed> feedList = new ArrayList<>();
        Image image1 = new Image("image-0-up", R.drawable.image1, "카페");
        Image image2 = new Image("image-0-down", R.drawable.image2, "술집");
        User user1 = new User("user-0", "박신혜", R.drawable.profile);
        Feed feed1 = new Feed(
                "feed-0",
                new Image[]{image1, image2},
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        Image image3 = new Image("image-1-up", R.drawable.jajang, "짜장면");
        Image image4 = new Image("image-1-down", R.drawable.jambong, "짬뽕");
        User user2 = new User("user-1", "공유", R.drawable.profile2);

        Feed feed2 = new Feed(
                "feed-0",
                new Image[]{image3, image4},
                user2,
                "짬뽕, 짜장면",
                "2019.01.29",
                false
        );

        feedList.add(feed1);
        feedList.add(feed2);
        feedList.add(feed1);
        feedList.add(feed2);

        return Single.just(feedList);
    }
}
