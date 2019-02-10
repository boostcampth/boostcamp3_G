package com.boostcamp.dreampicker.data.source.firestore.mapper;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.VoteSelectionItem;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteVoteItem;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FeedResponseMapper {

    @NonNull
    public static Feed getFeed(@NonNull final FeedRemoteData data) {
        final FeedRemoteVoteItem itemA = data.getItemA();
        final FeedRemoteVoteItem itemB = data.getItemB();

        final int countA = getVoteCount(data.getVotedUserMap(), itemA.getId());
        final int countB = getVoteCount(data.getVotedUserMap(), itemB.getId());

        return new Feed(data.getId(),
                data.getUser(),
                data.getContent(),
                data.getDate(),
                getVoteSelectionItem(itemA, countA),
                getVoteSelectionItem(itemB, countB),
                getMySelection(data.getVotedUserMap(), data.getUser().getId()));
    }

    @NonNull
    private static VoteSelectionItem getVoteSelectionItem(@NonNull final FeedRemoteVoteItem item,
                                                          final int voteCount) {
        return new VoteSelectionItem(item.getId(), item.getImageUrl(), item.getTagList(), voteCount);
    }

    private static int getVoteCount(@Nullable final Map<String, String> map,
                             @NonNull String itemId) {
        if(map == null || map.isEmpty()) {
            return 0;
        } else {
            int count = 0;
            for(final Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getValue().equals(itemId)) {
                    count++;
                }
            }
            return count;
        }
    }

    @Nullable
    private static String getMySelection(@Nullable Map<String, String> map,
                                  @NonNull String userId) {
        if(map == null || map.isEmpty()) {
            return null;
        } else {
            return map.get(userId);
        }
    }
}
