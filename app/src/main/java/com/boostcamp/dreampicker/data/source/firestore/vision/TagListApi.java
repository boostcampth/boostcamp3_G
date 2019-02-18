package com.boostcamp.dreampicker.data.source.firestore.vision;

import com.boostcamp.dreampicker.data.source.firestore.vision.model.getTagListResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TagListApi {

    @FormUrlEncoded
    @POST("vision/multitag/generate")
    Single<getTagListResponse> getTagList(@Field("imageUrl") String imageUrl);

}
