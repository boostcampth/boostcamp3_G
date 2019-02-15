package com.boostcamp.dreampicker.di.component;

import com.boostcamp.dreampicker.di.scope.ActivityScope;
import com.boostcamp.dreampicker.di.module.MainModule;
import com.boostcamp.dreampicker.presentation.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> { }
}