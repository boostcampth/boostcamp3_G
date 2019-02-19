package com.boostcamp.dreampicker.di.module;

import android.app.Application;
import android.content.Context;

import com.boostcamp.dreampicker.data.remote.vision.RetrofitClient;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {
    @SuppressWarnings("unused")
    @Binds
    abstract Context bindContext(Application application);

    @Provides
    @Singleton
    static RetrofitClient provideRetrofit() {
        return new RetrofitClient();
    }
}