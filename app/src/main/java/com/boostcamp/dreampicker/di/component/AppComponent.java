package com.boostcamp.dreampicker.di.component;

import com.boostcamp.dreampicker.App;
import com.boostcamp.dreampicker.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class})
public interface AppComponent {
    void inject(App app);
}
