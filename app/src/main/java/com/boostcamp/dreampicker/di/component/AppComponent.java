package com.boostcamp.dreampicker.di.component;

import android.app.Application;

import com.boostcamp.dreampicker.App;
import com.boostcamp.dreampicker.di.module.AppModule;
import com.boostcamp.dreampicker.di.module.FirebaseModule;
import com.boostcamp.dreampicker.di.module.activity.ActivityBindingModule;
import com.boostcamp.dreampicker.di.module.repository.FeedRepositoryModule;
import com.boostcamp.dreampicker.di.module.repository.UserRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class,
        FirebaseModule.class,
        FeedRepositoryModule.class,
        UserRepositoryModule.class,
        ActivityBindingModule.class,
        AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
