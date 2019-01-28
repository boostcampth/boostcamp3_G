package com.boostcamp.dreampicker.view.feed;

import com.boostcamp.dreampicker.R;

import java.util.ArrayList;
import java.util.List;

public class FeedRepositoryImpl implements FeedRepository {
    @Override
    public List<Feed> getAllFeed() {
        List<Feed> feeds = new ArrayList<>();

        Image image1 = new Image("image-0-up", R.drawable.image1, new String[]{"밥먹고", "카페"});
        Image image2 = new Image("image-0-down", R.drawable.image2, new String[]{"분위기좋은술집", "가볍게", "술한잔"});
        User user1 = new User("user-0", "박신혜", R.drawable.profile);
        Feed feed1 = new Feed(
                "feed-0",
                new Image[]{image1, image2},
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        Image image3 = new Image("image-0-up", R.drawable.image1, new String[]{"밥먹고", "카페"});
        Image image4 = new Image("image-0-down", R.drawable.image2, new String[]{"분위기좋은술집", "가볍게", "술한잔"});
        User user2 = new User("user-0", "박신혜", R.drawable.profile);

        Feed feed2 = new Feed(
                "feed-0",
                new Image[]{image3, image4},
                user2,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        feeds.add(feed1);
        feeds.add(feed2);

        return feeds;
    }
}
