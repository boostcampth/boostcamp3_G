package com.boostcamp.dreampicker.data.local.room.dao;

import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;

@Dao
public interface VotedFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(final VotedFeed votedFeed);

    @Query("SELECT * FROM voted_feeds ORDER BY date DESC")
    DataSource.Factory<Integer, VotedFeed> selectAll();
}
