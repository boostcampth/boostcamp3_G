package com.boostcamp.dreampicker.data.remote.firestore.mapper;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedDetail;
import com.boostcamp.dreampicker.data.model.VoteSelectionItem;
import com.boostcamp.dreampicker.data.remote.firestore.model.FeedItemRemoteData;
import com.boostcamp.dreampicker.data.remote.firestore.model.FeedRemoteData;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FeedResponseMapper {

    @NonNull
    public static Feed toFeed(@NonNull final String userId,
                              @NonNull final String feedId,
                              @NonNull final FeedRemoteData data) {
        final FeedItemRemoteData itemA = data.getItemA();
        final FeedItemRemoteData itemB = data.getItemB();

        final int countA = getVoteCount(data.getVotedUserMap(), itemA.getId());
        final int countB = getVoteCount(data.getVotedUserMap(), itemB.getId());

        return new Feed(feedId,
                data.getWriter(),
                data.getContent(),
                data.getDate(),
                getVoteSelectionItem(itemA, countA),
                getVoteSelectionItem(itemB, countB),
                getMySelection(data.getVotedUserMap(), userId));
    }

    @NonNull
    public static FeedDetail toFeedDetail(@NonNull final String userId,
                                          @NonNull final String feedId,
                                          @NonNull final FeedRemoteData data,
                                          final boolean isEnded) {
        final FeedItemRemoteData itemA = data.getItemA();
        final FeedItemRemoteData itemB = data.getItemB();

        final int countA = getVoteCount(data.getVotedUserMap(), itemA.getId());
        final int countB = getVoteCount(data.getVotedUserMap(), itemB.getId());

        return new FeedDetail(feedId,
                data.getWriter(),
                data.getContent(),
                data.getDate(),
                getVoteSelectionItem(itemA, countA),
                getVoteSelectionItem(itemB, countB),
                getMySelection(data.getVotedUserMap(), userId),
                isEnded);
    }

    @NonNull
    private static VoteSelectionItem getVoteSelectionItem(@NonNull final FeedItemRemoteData item,
                                                          final int voteCount) {
        return new VoteSelectionItem(item.getId(), item.getImageUrl(), item.getTagList(), voteCount);
    }

    private static int getVoteCount(@Nullable final Map<String, String> map,
                                    @NonNull String itemId) {
        if (map == null || map.isEmpty()) {
            return 0;
        } else {
            int count = 0;
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().equals(itemId)) {
                    count++;
                }
            }
            return count;
        }
    }

    @Nullable
    private static String getMySelection(@Nullable final Map<String, String> map,
                                         @NonNull final String userId) {
        if (map == null || map.isEmpty()) {
            return null;
        } else {
            return map.get(userId);
        }
    }
}
