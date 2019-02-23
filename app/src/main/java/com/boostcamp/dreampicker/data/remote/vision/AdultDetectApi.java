package com.boostcamp.dreampicker.data.remote.vision;

import com.boostcamp.dreampicker.data.remote.vision.model.AdultDetectResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AdultDetectApi {

    @FormUrlEncoded
    @POST("/v1/vision/adult/detect")
    Single<AdultDetectResponse> getAdultDetectResult(@Field("image_url") String imageUrl);

}
