package com.boostcamp.dreampicker.di.module;

import com.boostcamp.dreampicker.di.component.MainComponent;
import com.boostcamp.dreampicker.presentation.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainComponent.class})
public abstract class AppModule {
    @Binds
    @IntoMap
    @ClassKey(MainActivity.class)
    abstract AndroidInjector.Factory<?> bindMainActivity(MainComponent.Builder builder);
}