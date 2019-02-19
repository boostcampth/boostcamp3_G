package com.boostcamp.dreampicker.data.source.firestore.vision;

import com.boostcamp.dreampicker.data.source.firestore.vision.model.getAdultDetectResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AdultDetectApi {

    @FormUrlEncoded
    @POST("/v1/vision/adult/detect")
    Single<getAdultDetectResponse> getTagList(@Field("image_url") String imageUrl);

}
