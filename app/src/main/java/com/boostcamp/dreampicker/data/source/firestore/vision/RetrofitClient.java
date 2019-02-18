package com.boostcamp.dreampicker.data.source.firestore.vision;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://kapi.kakao.com/v1/";

    private static final String AUTHORIZTION = "84f0b51c106fe7410014a24ec2eca7cf";

    private static Retrofit retrofit = null;

    private RetrofitClient() { }

    public static Retrofit getInstance() {

        if (retrofit == null ) {
            Interceptor interceptor = chain -> {
                final Request.Builder builder = chain.request().newBuilder()
                        .header("Authorization", AUTHORIZTION);

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

        return retrofit;

    }

}
