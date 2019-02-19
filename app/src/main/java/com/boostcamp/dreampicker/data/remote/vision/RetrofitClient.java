package com.boostcamp.dreampicker.data.remote.vision;

import com.boostcamp.dreampicker.data.remote.vision.model.getAdultDetectResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RetrofitClient {

    private static final String BASE_URL = "https://kapi.kakao.com";

    private static final String AUTHORIZATION_KEY = "KakaoAK 84f0b51c106fe7410014a24ec2eca7cf";
    private static final String AUTHORIZATION = "Authorization";

    private static Retrofit retrofit = null;

    @Inject
    public RetrofitClient() {
        if (retrofit == null) {
            Interceptor interceptor = chain -> {
                final Request.Builder builder = chain.request().newBuilder()
                        .header(AUTHORIZATION, AUTHORIZATION_KEY);

                return chain.proceed(builder.build());
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public Single<getAdultDetectResponse> getAdultDetectResult(String imageUrl) {
        return retrofit.create(AdultDetectApi.class)
                .getAdultDetectResult(imageUrl)
                .subscribeOn(Schedulers.newThread());
    }

}
