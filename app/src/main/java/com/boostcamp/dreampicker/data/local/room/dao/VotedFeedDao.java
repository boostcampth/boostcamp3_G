package com.boostcamp.dreampicker.data.local.room.dao;

import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface VotedFeedDao {
    @Insert
    Completable insert(final VotedFeed votedFeed);

    @Query("SELECT * FROM voted_feeds")
    Single<List<VotedFeed>> getVotedFeedList();
}
